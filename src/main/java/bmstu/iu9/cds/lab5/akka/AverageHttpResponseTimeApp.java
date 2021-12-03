package bmstu.iu9.cds.lab5.akka;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.Query;

import akka.japi.Pair;

import akka.japi.function.Function2;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Response;
import scala.compat.java8.FutureConverters;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

public class AverageHttpResponseTimeApp {
    private static final int    TIMEOUT_MILLISEC = 5000;

    public static void main(String[] args) throws IOException {
        System.out.println("start!");
        ActorSystem system = ActorSystem.create("routes");
        ActorRef actor = system.actorOf(Props.create(ActorCache.class));

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = flowHttpRequest(system, materializer, actor);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost("localhost", 8080),
                materializer
        );
        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());
    }

    private static Flow<HttpRequest, HttpResponse, NotUsed> flowHttpRequest(
            ActorSystem system, ActorMaterializer materializer, ActorRef actor) {
        return Flow.of(HttpRequest.class)
                .map( req -> {
                    Query query = req.getUri().query();
                    String url = query.get("testUrl").get();
                    int count = Integer.parseInt(query.get("count").get());
                    return new Pair<>(url, count);
                })
                .mapAsync(1, req ->
                        FutureConverters.toJava(
                                Patterns.ask(
                                        actor,
                                        new MessageGetResult(req.first()),
                                        TIMEOUT_MILLISEC
                                )
                        ).thenCompose( res -> {
                            if (res != null) {
                                return CompletableFuture.completedFuture(new Pair<>(req.first(), res));
                            } else {
                                Sink<Pair<String, Integer>, CompletionStage<Double>> flow = Flow.<Pair<String, Integer>>create()
                                        .mapConcat(r -> new ArrayList<>(Collections.nCopies(r.second(), r.first())))
                                        .mapAsync(req.second(), r -> {
                                            long start = System.currentTimeMillis();
                                            Dsl.asyncHttpClient().prepareGet("http://www.example.com/").execute();
                                            long end = System.currentTimeMillis();
                                            return CompletableFuture.completedFuture(start - end);
                                        }).toMat(Sink.fold(0, Long::sum), Keep.right());
                                Sink<Long, CompletionStage<Long>> sink = Sink.fold(0, (agg, next) -> agg + next);
                                return Source.from(Collections.singletonList(req))
                                        .toMat(sink, Keep.right()).run(materializer)
                            }
                        })
                )
                .map(res -> {
                    actor.tell(
                            new MessageCacheResult(res.first(), res.second()),
                            ActorRef.noSender()
                    );
                    return HttpResponse.create().entity(res.first() + ": " + res.second());
                });

    }

    static class MessageGetResult {
        private final String url;

        public MessageGetResult(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }

    static class MessageCacheResult {
        private final String url;
        private final long   responseTime;

        public MessageCacheResult(String url, long responseTime) {
            this.url = url;
            this.responseTime = responseTime;
        }

        public String getUrl() {
            return url;
        }

        public long getResponseTime() {
            return responseTime;
        }
    }
}
