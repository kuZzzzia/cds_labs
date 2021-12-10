package bmstu.iu9.cds.lab6.zookeeper;

import akka.actor.ActorRef;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;

import java.time.Duration;

import static akka.actor.Nobody.path;
import static akka.http.javadsl.server.Directives.*;

public class HttpServer {
    private static final String     PATH = "";
    private static final String     URL_QUERY_PARAM = "url";
    private static final String     COUNT_QUERY_PARAM = "count";
    private static final String     ZERO_COUNT_STRING = "0";
    private static final Duration   TIMEOUT = Duration.ofMillis(5000);
    private static final String     URL_PATTERN = "http://localhost%s/?url=%s&count=%d";

    private final Http        http;
    private final ActorRef    actorConfig;
    private final int         serverNumber;


    public HttpServer(Http http, ActorRef actorConfig, int serverNumber) {
        this.http = http;
        this.actorConfig = actorConfig;
        this.serverNumber = serverNumber;
    }

    private Route route (ActorRef actorConfig) {
        return route(
                path(PATH, () ->
                        get(() ->
                                parameter(URL_QUERY_PARAM, url ->
                                        parameter(COUNT_QUERY_PARAM, count -> {
                                            if (count.equals(ZERO_COUNT_STRING)) {
                                                return completeWithFuture(
                                                        http.singleRequest(
                                                                HttpRequest.create(url)
                                                        )
                                                );
                                            } else {
                                                return Patterns.ask(
                                                        actorConfig,
                                                        new MessageGetRandomServerUrl(serverNumber),
                                                        TIMEOUT
                                                ).thenCompose(resPort ->
                                                        http.singleRequest(
                                                                HttpRequest.create(
                                                                        String.format(URL_PATTERN, resPort, url, Integer.parseInt(count) - 1)
                                                                )
                                                        )
                                                ));
                                            }
                                })
                        ))

                )
        );
    }

    static class MessageGetRandomServerUrl {
        private final int serverNumber;

        public MessageGetRandomServerUrl(int serverNumber) {
            this.serverNumber = serverNumber;
        }

        public int getServerNumber() {
            return serverNumber;
        }
    }

}
