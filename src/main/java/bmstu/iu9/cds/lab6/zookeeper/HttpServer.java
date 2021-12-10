package bmstu.iu9.cds.lab6.zookeeper;

import akka.actor.ActorRef;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;

import java.time.Duration;

import static akka.actor.Nobody.path;
import static akka.http.javadsl.server.Directives.get;
import static akka.http.javadsl.server.Directives.parameter;

public class HttpServer {
    private static final String URL_QUERY_PARAM = "url";
    private static final String COUNT_QUERY_PARAM = "count";
    private static final String ZERO_COUNT_STRING = "0";
    private static final Duration TIMEOUT = Duration(5000);

    private Http http;
    private ActorRef actorConfig;


    public HttpServer(Http http) {
        this.http = http;
    }

    private Route route (ActorRef actorConfig) {
        return route(
                path( "", () ->
                        get(() ->
                        parameter(URL_QUERY_PARAM, (url) ->
                                parameter(COUNT_QUERY_PARAM, (count) -> {
                                    if (count.equals(ZERO_COUNT_STRING)) {
                                        return http.singleRequest(HttpRequest.create(url));
                                    } else {
                                        Patterns.ask(actorConfig, new )
                                    }
                                })
                                )
                        )

                )
        );
    }

    static class MessageGetRandomServerUrl() {
        private
    }

}
