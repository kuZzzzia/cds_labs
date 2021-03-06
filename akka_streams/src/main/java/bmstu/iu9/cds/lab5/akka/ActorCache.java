package bmstu.iu9.cds.lab5.akka;

import akka.actor.AbstractActor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ActorCache extends AbstractActor {

    private final Map<String, Long> results = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        AverageHttpResponseTimeApp.MessageGetResult.class,
                        message -> sender().tell(
                                Optional.ofNullable(results.get(message.getUrl())),
                                self())
                )
                .match(
                        AverageHttpResponseTimeApp.MessageCacheResult.class,
                        message -> results.put(message.getUrl(), message.getResponseTime())
                )
                .build();
    }

}
