package bmstu.iu9.cds.lab4.akka;

import akka.actor.AbstractActor;

import java.util.HashMap;
import java.util.Map;

public class ActorKeeper extends AbstractActor {
    private Map<Integer, TestResult> results = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        StoreTestResultMessage.class,
                        m -> {
                            results.put(m.getPackageId(), m.getTestResult());
                            System.out.println("Received message: " + m);
                        })
                .match(
                        GetTestResultMessage.class,
                        req -> sender().tell(
                                new 
                        ))
                .build();
    }
}
