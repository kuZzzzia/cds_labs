package bmstu.iu9.cds.lab6.zookeeper;

import akka.actor.ActorRef;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZooKeeperWatcher implements Watcher {
    private static final String SERVERS_PATH = "/servers";
    private static final String CLIENT_PATH = "localhost:";

    private final ZooKeeper zooKeeper;
    private final ActorRef  actorConfig;

    public ZooKeeperWatcher(String servers, ActorRef actorConfig) throws IOException, InterruptedException, KeeperException {
        this.actorConfig = actorConfig;

        zooKeeper = new ZooKeeper(servers, 3000, this);

        zooKeeper.create("/servers",
                "lab6".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL
        );

        
    }

    private void sendServers() throws InterruptedException, KeeperException {
        List<String> servers = new ArrayList<>();
        for (String s : zooKeeper.getChildren(SERVERS_PATH, this)) {
            servers.add(new String(zooKeeper.getData("/servers/" + s, false, null)));
        }
        actorConfig.tell(new MessageSendServersList(servers),  ActorRef.noSender());
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        try {
            sendServers();
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    static class MessageSendServersList {
        private final List<String> servers;

        MessageSendServersList(List<String> servers) {
            this.servers = servers;
        }

        public List<String> getServers() {
            return servers;
        }
    }

}
