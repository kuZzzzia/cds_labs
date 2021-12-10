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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;

public class AnonymizeApp {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";


    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.err.println("Usage: AnonymizeApp localhost:2181 8000 8001");
            System.exit(-1);
        }
        BasicConfigurator.configure();
        printInGreen("start!\n" + Arrays.toString(args));
        ActorSystem system = ActorSystem.create("lab6");
        ActorRef actorConfig = system.actorOf(Props.create(ActorConfig.class));
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        final Http http = Http.get(system);
        ZooKeeper zk = null;

        try {
            zk = new ZooKeeper(args[0], 3000, null);
            new ZooKeeperWatcher(zk, actorConfig);
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        List<CompletionStage<ServerBinding>> bindings = new ArrayList<>();

        StringBuilder serversInfo = new StringBuilder("Servers online at\n");
        for (int i = 1; i < args.length; i++) {
            HttpServer server = new HttpServer(http, actorConfig, zk, args[i]);
            final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = server.createRoute().flow(system, materializer);
            bindings.add(http.bindAndHandle(
                    routeFlow,
                    ConnectHttp.toHost("localhost", Integer.parseInt(args[i])),
                    materializer
            ));
            serversInfo.append("http://localhost:").append(args[i]).append("/\n");
        }



        printInGreen(serversInfo +
                "\nPress RETURN to stop...");

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        for (CompletionStage<ServerBinding> binding : bindings) {
            binding
                    .thenCompose(ServerBinding::unbind)
                    .thenAccept(unbound -> system.terminate());
        }
    }

    public static void printInGreen(String s) {
        System.out.println(ANSI_GREEN + s + ANSI_RESET);
    }

}
