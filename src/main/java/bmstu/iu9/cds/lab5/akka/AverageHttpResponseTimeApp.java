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
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.http.javadsl.unmarshalling.StringUnmarshallers;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import scala.compat.java8.FutureConverters;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AverageHttpResponseTimeApp extends AllDirectives{
    private static final String EMPTY_PATH = "";
    private static final int    TIMEOUT_MILLISEC = 5000;

    public static void main(String[] args) throws IOException {
        System.out.println("start!");
        ActorSystem system = ActorSystem.create("routes");
        ActorRef actor = system.actorOf(Props.create(ActorCache.class));

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        AverageHttpResponseTimeApp instance = new AverageHttpResponseTimeApp();
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = instance.createRoute(system, materializer, actor).flow(system, materializer);
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

    private Route createRoute(ActorSystem system, ActorMaterializer materializer, ActorRef actor) {
        return parameter("testUrl", url ->
                parameter(StringUnmarshallers.INTEGER, "count", count ->
                        completeOKWithFuture(
                                FutureConverters.toJava(
                                        Patterns.ask(
                                                actor,
                                                new MessageGetResult(url),
                                                TIMEOUT_MILLISEC
                                        )).thenCompose(res ->
                                                (res != null)
                                                        ? CompletableFuture.completedFuture(res)
                                                        :
                        ))
        ));
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
