package bmstu.iu9.cds.lab4.akka;

import akka.actor.AbstractActor;

import java.util.Map;

public class ActorKeeper extends AbstractActor {
    private Map<Integer, TestResultMessage> results;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        TestResultMessage.class,
                        m -> {
                            results.put(m.getPackageId(), )
                        }
                        )
    }
}
