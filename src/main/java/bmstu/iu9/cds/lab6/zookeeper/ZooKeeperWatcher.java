package bmstu.iu9.cds.lab6.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

public class ZooKeeperWatcher implements Watcher {
    private final ZooKeeper zooKeeper;

    public ZooKeeperWatcher(String servers) throws IOException {
        zooKeeper = new ZooKeeper(servers, 3000, this);
        zoo.create("/servers/s",
                "data".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE ,
                CreateMode.EPHEMERAL_SEQUENTIAL
        );
        List<String> servers = zoo.getChildren("/servers", this);
        for (String s : servers) {
            byte[] data = zoo.getData("/servers/" + s, false, null);
            System.out.println("server " + s + " data=" + new String(data));
        }
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
