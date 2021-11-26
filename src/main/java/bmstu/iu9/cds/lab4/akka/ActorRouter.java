package bmstu.iu9.cds.lab4.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.routing.Router;

public class ActorRouter extends AbstractActor {
    private final ActorRef keeper;
    private final Router router;

    @Override
    public Receive createReceive() {
        return null;
    }
}
