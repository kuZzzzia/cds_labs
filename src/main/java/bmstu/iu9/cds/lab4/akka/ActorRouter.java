package bmstu.iu9.cds.lab4.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;

import java.util.ArrayList;
import java.util.List;

public class ActorRouter extends AbstractActor {
    private static final int TESTERS_AMOUNT = 5;

    private final ActorRef keeper;
    private final Router router;

    {
        keeper = getContext().actorOf(Props.create(ActorKeeper.class));
        List<Routee> routees = new ArrayList<>();
        for (int i = 0; i < TESTERS_AMOUNT; i++) {
            ActorRef r = getContext().actorOf(ActorTester.props(keeper));
            getContext().watch(r);
            routees.add(new ActorRefRoutee(r));
        }
        router = new Router(new RoundRobinRoutingLogic(), routees);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match()
                .build();
    }
}
