package bmstu.iu9.cds.lab6.zookeeper;

import akka.actor.ActorRef;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.time.Duration;

import static akka.http.javadsl.server.Directives.*;

public class HttpServer implements Watcher {
    private static final String     PATH = "";
    private static final String     URL_QUERY_PARAM = "url";
    private static final String     COUNT_QUERY_PARAM = "count";
    private static final String     ZERO_COUNT_STRING = "0";
    private static final Duration   TIMEOUT = Duration.ofMillis(5000);
    private static final String     URL_PATTERN = "%s/?url=%s&count=%d";

    private final Http        http;
    private final ActorRef    actorConfig;


    public HttpServer(Http http, ActorRef actorConfig) {
        this.http = http;
        this.actorConfig = actorConfig;
    }

    public Route createRoute() {
        return route(
                path(PATH, () ->
                        route(
                                get(() ->
                                        parameter(URL_QUERY_PARAM, (url) ->
                                        parameter(COUNT_QUERY_PARAM, (count) -> {
                                            if (count.equals(ZERO_COUNT_STRING)) {
                                                return completeWithFuture(
                                                        http.singleRequest(HttpRequest.create(url))
                                                );
                                            }
                                            return completeWithFuture(
                                                    Patterns
                                                            .ask(
                                                                    actorConfig,
                                                                    new MessageGetRandomServerUrl(),
                                                                    TIMEOUT
                                                            )
                                                            .thenCompose(resPort ->
                                                                    http.singleRequest(HttpRequest.create(
                                                                            String.format(
                                                                                    URL_PATTERN,
                                                                                    resPort,
                                                                                    url,
                                                                                    Integer.parseInt(count) - 1
                                                                            )
                                                                    ))
                                                            ));
                                        })

                                ))
                        )
                )
        );
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

    }

    static class MessageGetRandomServerUrl {

        public MessageGetRandomServerUrl() {}

    /*
        private final int portNumber;



        public int getPortNumber() {
            return portNumber;
        }
     */
    }

}
