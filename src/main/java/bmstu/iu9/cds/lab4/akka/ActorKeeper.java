package bmstu.iu9.cds.lab4.akka;

import akka.actor.AbstractActor;

import java.util.*;

public class ActorKeeper extends AbstractActor {
    private final Map<Integer, List<TestResult>> results = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        StoreTestResultMessage.class,
                        m -> {
                            int packageId = m.getPackageId();
                            if (results.containsKey(packageId)) {
                                results.get(packageId).add(m.getTestResult());
                            } else {
                                results.put(
                                        m.getPackageId(),
                                        new ArrayList<>(
                                                Collections.singleton(m.getTestResult())
                                        )
                                );
                            }
                            System.out.println("Received message: " + m);
                        })
                .match(
                        GetTestResultMessage.class,
                        req -> sender().tell(
                                new GetTestsPackageMessage(
                                        req.getPackageID(),
                                        results.get(req.getPackageID())
                                );
                        ))
                .build();
    }
}
