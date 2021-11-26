package bmstu.iu9.cds.lab4.akka;

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
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;


import java.io.IOException;
import java.util.concurrent.CompletionStage;

public class JSTestApp extends AllDirectives {
    private static final String ACTOR_SYSTEM_NAME = "js test app";

    public static void main(String[] args) throws IOException {
        ActorSystem actorSystem = ActorSystem.create(ACTOR_SYSTEM_NAME);
        ActorRef actorRouter = actorSystem.actorOf(Props.create(ActorRouter.class));

//        final Http http = Http.get(system);
//        final ActorMaterializer materializer = ActorMaterializer.create(system);
//
//        //In order to access all directives we need an instance where the routes are define.
//        HttpServerMinimalExampleTest app = new HttpServerMinimalExampleTest();
//
//        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = app.createRoute().flow(system, materializer);
//        final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow,
//                ConnectHttp.toHost("localhost", 8080), materializer);


        final Http http = Http.get(actorSystem);
        final ActorMaterializer materializer = ActorMaterializer.create(actorSystem);
        JSTestApp instance = new JSTestApp();
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow =
                instance.createRoute(actorSystem).flow(actorSystem, materializer);
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

    private Route createRoute() {
        return route(
                path("hello", () ->
                        get(() ->
                                complete("<h1>Say hello to akka-http</h1>"))));
    }

}
