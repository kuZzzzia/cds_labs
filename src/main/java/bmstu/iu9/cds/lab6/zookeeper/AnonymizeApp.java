package bmstu.iu9.cds.lab6.zookeeper;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

public class AnonymizeApp {

    public static void main(String[] args) {
        System.out.println("start!");
        ActorSystem system = ActorSystem.create();
        ActorRef actorConfig = system.actorOf(Props.create(ActorConfig.class));
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        final Http http = Http.get(system);

        final HttpServer server = new HttpServer(http, actorConfig, "8080");

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = server.route();
    }

}
