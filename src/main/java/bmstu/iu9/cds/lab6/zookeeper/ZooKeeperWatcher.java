package bmstu.iu9.cds.lab6.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

public class ZooKeeperWatcher implements Watcher {
    private final ZooKeeper zooKeeper;

    public ZooKeeperWatcher(String servers) throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper(servers, 3000, this);
        zooKeeper.create("/servers/s",
                "data".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE ,
                CreateMode.EPHEMERAL_SEQUENTIAL
        );
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
