package bmstu.iu9.cds.lab6.zookeeper;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.Http;

public class AnonymizeApp {

    public static void main(String[] args) {
        System.out.println("start!");
        ActorSystem system = ActorSystem.create();
        ActorRef actorConfig = system.actorOf(Props.create(ActorConfig.class));
        

        final Http http = Http.get(system);
    }

}
