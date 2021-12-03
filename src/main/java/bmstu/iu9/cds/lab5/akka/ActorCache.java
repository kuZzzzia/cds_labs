package bmstu.iu9.cds.lab5.akka;

import akka.actor.AbstractActor;

import java.util.HashMap;
import java.util.Map;

public class ActorCache extends AbstractActor {
    private final Map<String, Long> results = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        AverageHttpResponseTimeApp.MessageGetResult.class,
                        message -> sender().tell(
                                new MessageReturnResponseTime(
                                        results.get(message.getUrl())
                                ),
                                self())
                )
                .match(
                        AverageHttpResponseTimeApp.MessageCacheResult.class,
                        message -> results.put(message.getUrl(), message.getResponseTime())
                )
                .build();
    }

    static class MessageReturnResponseTime {
        private final long responseTime;

        public MessageReturnResponseTime(long responseTime) {
            this.responseTime = responseTime;
        }

        public long getResponseTime() {
            return responseTime;
        }
    }
}
