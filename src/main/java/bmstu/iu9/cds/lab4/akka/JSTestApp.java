package bmstu.iu9.cds.lab4.akka;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import scala.concurrent.Future;


import java.io.IOException;
import java.util.concurrent.CompletionStage;

public class JSTestApp extends AllDirectives {
    private static final String ACTOR_SYSTEM_NAME = "js_test_app";

    public static void main(String[] args) throws IOException {
        ActorSystem actorSystem = ActorSystem.create(ACTOR_SYSTEM_NAME);
        ActorRef actorRouter = actorSystem.actorOf(Props.create(ActorRouter.class));


        final Http http = Http.get(actorSystem);
        final ActorMaterializer materializer = ActorMaterializer.create(actorSystem);
        JSTestApp instance = new JSTestApp();
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow =
                instance.createRoute(actorRouter).flow(actorSystem, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost("localhost", 8080),
                materializer
        );
        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> actorSystem.terminate());

    }

    private Route createRoute(ActorRef actorRouter) {
        return route(
                path("test", () ->
                        route(
                                post(() ->
                                        entity())
                        )),
                path("result", () ->
                    route(
                            get(
                                    () -> {
                                        Future<Object> result = Patterns.ask(actorRouter, new GetTestPackageResultMessage(id), 5000);
                                        return complete(result, Jackson.marshaller());
                                    }
                            )
                    )
                ),
                path("result", () ->
                        put(() ->
                                parameter("packageId", (id) ->
                                        parameter("results", (res) -> {
                                                                                    })))));
    }



}
