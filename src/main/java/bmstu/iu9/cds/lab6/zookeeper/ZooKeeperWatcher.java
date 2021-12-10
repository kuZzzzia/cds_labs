package bmstu.iu9.cds.lab6.zookeeper;

import akka.actor.ActorRef;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

public class ZooKeeperWatcher implements Watcher {
    private static final String SERVERS_PATH = "/servers";

    private final ZooKeeper zooKeeper;
    private final ActorRef  actorConfig;

    public ZooKeeperWatcher(String servers, ActorRef actorConfig) throws IOException, InterruptedException, KeeperException {
        this.actorConfig = actorConfig;

        zooKeeper = new ZooKeeper(servers, 3000, this);
        zooKeeper.create("/servers/s",
                "data".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE ,
                CreateMode.EPHEMERAL_SEQUENTIAL
        );

    }

    private List<String> sendServers() throws InterruptedException, KeeperException {
        return zooKeeper.getChildren(SERVERS_PATH, this);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

    }

    static class MessageSendServersList {
        private List<String> servers;

        MessageSendServersList() {

        }

        public List<String> getServers() {
            return servers;
        }
    }


}
