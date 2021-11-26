package bmstu.iu9.cds.lab4.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.routing.Routee;
import akka.routing.Router;

import java.util.ArrayList;
import java.util.List;

public class ActorRouter extends AbstractActor {
    private final ActorRef keeper;
    private final Router router;

    {
        List<Routee> routees = new ArrayList<Routee>();
        for (int i = 0; i < 5; i++) {
            ActorRef r = getContext().actorOf(Props.create(Worker.class));
            getContext().watch(r);
            routees.add(new ActorRefRoutee(r));
        }
        router = new Router(new RoundRobinRoutingLogic(), routees);
    }

    @Override
    public Receive createReceive() {
        return null;
    }
}
