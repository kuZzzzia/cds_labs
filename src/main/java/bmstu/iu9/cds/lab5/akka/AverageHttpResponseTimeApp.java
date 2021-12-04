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

import org.asynchttpclient.Request;
import org.asynchttpclient.Response;
import scala.compat.java8.FutureConverters;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AverageHttpResponseTimeApp {
    private static final String QUERY_PARAMETER_URL = "testUrl";
    private static final String QUERY_PARAMETER_COUNT = "count";

    private static final int    TIMEOUT_MILLISEC = 5000;
    private static final int    MAP_PARALLELISM_FOR_EACH_GET_REQUEST = 1;
    private static final int    PORT = 8080;

    public static void main(String[] args) throws IOException {
        System.out.println("start!");
        ActorSystem system = ActorSystem.create("routes");
        ActorRef actor = system.actorOf(Props.create(ActorCache.class));

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = flowHttpRequest(materializer, actor);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost("localhost", PORT),
                materializer
        );
        System.out.println("Server online at http://localhost:" + PORT + "/\nPress RETURN to stop...");
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());
    }

    private static Flow<HttpRequest, HttpResponse, NotUsed> flowHttpRequest(
            ActorMaterializer materializer, ActorRef actor) {
        return Flow.of(HttpRequest.class)
                .map( req -> {
                    Query query = req.getUri().query();
                    String url = query.get(QUERY_PARAMETER_URL).get();
                    int count = Integer.parseInt(query.get(QUERY_PARAMETER_COUNT).get());
                    return new Pair<>(url, count);
                })
                .mapAsync(MAP_PARALLELISM_FOR_EACH_GET_REQUEST, req ->
                        FutureConverters.toJava(
                                Patterns.ask(
                                        actor,
                                        new MessageGetResult(req.first()),
                                        TIMEOUT_MILLISEC
                                )
                        ).thenCompose( res -> {
                            if (!(Objects.isNull(res))) {
                                return CompletableFuture.completedFuture(new Pair<>(req.first(), (long) ((Optional)res).get()));
                            } else {
                                Sink<Integer, CompletionStage<Long>> fold = Sink.fold(0L, (Function2<Long, Integer, Long>) Long::sum);

                                Sink<Pair<String, Integer>, CompletionStage<Long>> sink = Flow
                                        .<Pair<String, Integer>>create()
                                        .mapConcat(r -> new ArrayList<>(Collections.nCopies(r.second(), r.first())))
                                        .mapAsync(req.second(), url -> {
                                            long start = System.currentTimeMillis();
                                            Request request = Dsl.get(url).build();
                                            CompletableFuture<Response> whenResponse = Dsl.asyncHttpClient().executeRequest(request).toCompletableFuture();
                                            return whenResponse.thenCompose( response -> {
                                                        int duration = (int) (System.currentTimeMillis() - start);
                                                        return CompletableFuture.completedFuture(duration);
                                                    }
                                            );
                                        })
                                        .toMat(fold, Keep.right());

                                return Source.from(Collections.singletonList(req))
                                        .toMat(sink, Keep.right())
                                        .run(materializer)
                                        .thenApply(sum -> new Pair<>(req.first(), sum / req.second()));
                            }
                        })
                )
                .map(res -> {
                    actor.tell(
                            new MessageCacheResult(res.first(), res.second()),
                            ActorRef.noSender()
                    );
                    return HttpResponse.create().withEntity(res.first() + ": " + res.second().toString());
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
