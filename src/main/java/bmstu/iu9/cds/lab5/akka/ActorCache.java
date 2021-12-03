package bmstu.iu9.cds.lab5.akka;

import akka.actor.AbstractActor;

import java.util.HashMap;
import java.util.Map;

public class ActorCache extends AbstractActor {
    private Map<String, Long> results= new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        AverageHttpResponseTimeApp.MessageGetResult.class,
                        message -> {
                            AverageHttpResponseTimeApp.MessageGetResult.class,
                            
                        }
                )
                .match(
                        AverageHttpResponseTimeApp.MessageCacheResult.class,
                        message -> {

                        }
                )
                .build();
    }
}
