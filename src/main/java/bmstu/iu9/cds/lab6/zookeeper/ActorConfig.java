package bmstu.iu9.cds.lab6.zookeeper;

import akka.actor.AbstractActor;
import akka.actor.Actor;

import java.util.List;


public class ActorConfig extends AbstractActor {
    private List<String> servers;


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(HttpServer.MessageGetRandomServerUrl.class, msg -> sender().tell(), Actor.noSender())
                .match()
                .build();
    }

    return String rand
}
