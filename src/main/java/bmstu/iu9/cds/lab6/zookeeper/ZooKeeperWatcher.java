package bmstu.iu9.cds.lab6.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

public class ZooKeeperWatcher {

    private final ZooKeeper zooKeeper;

    public ZooKeeperWatcher() {
        ZooKeeper zoo = new ZooKeeper("127.0.0.1:2181MB", 3000, this);
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

    static class MessageSendServersList {
        private List<String> servers;

        MessageSendServersList() {

        }

        public List<String> getServers() {
            return servers;
        }
    }


}
