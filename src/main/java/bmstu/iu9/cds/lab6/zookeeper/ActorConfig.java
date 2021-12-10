package bmstu.iu9.cds.lab6.zookeeper;

import akka.actor.AbstractActor;

import java.util.List;


public class ActorConfig extends AbstractActor {
    private List<String> servers;


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match()
                .match()
                .build();
    }
}
