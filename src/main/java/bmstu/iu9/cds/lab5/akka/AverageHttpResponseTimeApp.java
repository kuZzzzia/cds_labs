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

import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;


import java.io.IOException;
import java.util.concurrent.CompletionStage;

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

    private static Flow<HttpRequest, HttpResponse, NotUsed> flowHttpRequest(ActorSystem system, ActorMaterializer materializer, ActorRef actor) {
        return Flow.of(HttpRequest.class)
                .map( req -> {
                    Query query = req.getUri().query();
                    String url = query.get("testUrl").get();
                    int count = Integer.parseInt(query.get("count").get());
                    return new Pair<>(url, count);
                        }
                ).mapAsync(1, req -> {
                    CompletionStage<Object> result = (CompletionStage<Object>) Patterns.ask(
                            actor,
                            new MessageGetResult(req.first()),
                            TIMEOUT_MILLISEC
                    );
                    return result.thenCompose();
                })

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
