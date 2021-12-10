package bmstu.iu9.cds.lab6.zookeeper;

import akka.actor.AbstractActor;
import akka.actor.Actor;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ActorConfig extends AbstractActor {
    private List<String> servers = new ArrayList<>();

    private final Random random = new Random();


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(HttpServer.MessageGetRandomServerUrl.class, msg -> sender().tell(getRandomServerPort(), Actor.noSender()))
                .match(ZooKeeperWatcher.MessageSendServersList.class, msg -> servers = msg.getServers())
                .build();
    }

    private String getRandomServerPort() {
        return servers.get(random.nextInt(servers.size()));
    }
}
