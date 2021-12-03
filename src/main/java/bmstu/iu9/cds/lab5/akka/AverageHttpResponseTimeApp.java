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
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import javafx.util.Pair;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

public class AverageHttpResponseTimeApp {
    public static void main(String[] args) throws IOException {
        System.out.println("start!");
        ActorSystem system = ActorSystem.create("routes");
        ActorRef cacheActor = system.actorOf(Props.create(CacheActor.class));

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        AverageHttpResponseTimeApp instance = new AverageHttpResponseTimeApp();
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = instance.cre
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

    private static Flow<HttpRequest, HttpResponse, NotUsed> flowRequests(Http http, ActorSystem system, ActorMaterializer materializer) {
        return Flow.of(HttpRequest.class).map(req -> req.).mapAsync()
    }

    static class MessageGetResult {

    }

    static class MessageCacheResult {

    }
}
