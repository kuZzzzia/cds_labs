package bmstu.iu9.cds.lab4.akka;

import akka.actor.AbstractActor;

import java.util.*;

public class ActorKeeper extends AbstractActor {
    private final Map<String, List<String>> results = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        MessageStoreTestResult.class,
                        this::storeResult
                )
                .match(
                        MessageGetTestPackageResult.class,
                        req -> sender().tell(
                                new MessageReturnResults(
                                        req.getPackageID(),
                                        results.get(req.getPackageID())
                                ),
                                self()
                        )
                )
                .build();
    }

    private void storeResult(MessageStoreTestResult m) {
        String packageId = m.getPackageId();
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
    }
}
