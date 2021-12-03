package bmstu.iu9.cds.lab5.akka;

import akka.actor.AbstractActor;

public class CacheActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        AverageHttpResponseTimeApp.MessageGetResult.class,
                        message -> {

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
