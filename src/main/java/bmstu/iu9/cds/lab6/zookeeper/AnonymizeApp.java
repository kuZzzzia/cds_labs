package bmstu.iu9.cds.lab6.zookeeper;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import org.apache.log4j.BasicConfigurator;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CompletionStage;

public class AnonymizeApp {

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        System.out.println("start!\n" + Arrays.toString(args));
        ActorSystem system = ActorSystem.create();
        ActorRef actorConfig = system.actorOf(Props.create(ActorConfig.class));
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        final Http http = Http.get(system);

        ZooKeeper zk = new ZooKeeper(args[0], 3000, null);
        ZooKeeperWatcher zooKeeperWatcher = new ZooKeeperWatcher(zk, actorConfig);

        final HttpServer server = new HttpServer(http, actorConfig);
        for (int i = 1; i < args.length; i++) {
            new HttpServer(http, actorConfig, zk, args[i]);
        }

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = server.createRoute().flow(system, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost("localhost", 8080),
                materializer
        );
        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());
    }

}
