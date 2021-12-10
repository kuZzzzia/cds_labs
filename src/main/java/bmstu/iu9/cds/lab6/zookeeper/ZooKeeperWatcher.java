package bmstu.iu9.cds.lab6.zookeeper;

import org.apache.zookeeper.ZooKeeper;

import java.util.List;

public class ZooKeeperWatcher {

    private final ZooKeeper zooKeeper;

    public ZooKeeperWatcher() {

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
