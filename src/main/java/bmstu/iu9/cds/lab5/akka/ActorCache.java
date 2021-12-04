package bmstu.iu9.cds.lab5.akka;

import akka.actor.AbstractActor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ActorCache extends AbstractActor {
    private static final int NOT_MEASURED_YET_VALUE = -1000;

    private final Map<String, Integer> results = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        AverageHttpResponseTimeApp.MessageGetResult.class,
                        message -> sender().tell(
                                Optional.of(results.getOrDefault(message.getUrl(), NOT_MEASURED_YET_VALUE)),
                                self())
                )
                .match(
                        AverageHttpResponseTimeApp.MessageCacheResult.class,
                        message -> results.put(message.getUrl(), message.getResponseTime())
                )
                .build();
    }

}
