package bmstu.iu9.cds.lab6.zookeeper;

import akka.actor.ActorRef;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZooKeeperWatcher implements Watcher {
    private static final String SERVERS_PATH = "/servers";

    private final ZooKeeper zooKeeper;
    private final ActorRef  actorConfig;

    public ZooKeeperWatcher(ZooKeeper zooKeeper, ActorRef actorConfig) throws IOException, InterruptedException, KeeperException {
        this.actorConfig = actorConfig;

        this.zooKeeper = zooKeeper;

        this.zooKeeper.create(SERVERS_PATH,
                "lab6".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL
        );

        byte[] data = this.zooKeeper.getData(SERVERS_PATH , true, null);
        System.out.println("servers" + " data=" + new String(data));
    }

    private void sendServers() throws InterruptedException, KeeperException {
        List<String> servers = new ArrayList<>();
        for (String s : zooKeeper.getChildren(SERVERS_PATH, this)) {
            servers.add(new String(zooKeeper.getData(SERVERS_PATH + "/" + s, false, null)));
        }
        actorConfig.tell(new MessageSendServersList(servers),  ActorRef.noSender());
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        try {
            zooKeeper.getChildren(SERVERS_PATH, this);
            sendServers();
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
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
