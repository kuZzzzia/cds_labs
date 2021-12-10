package bmstu.iu9.cds.lab6.zookeeper;

import akka.actor.ActorRef;
import akka.http.javadsl.Http;
import akka.http.javadsl.server.Route;

import static akka.actor.Nobody.path;
import static akka.http.javadsl.server.Directives.get;
import static akka.http.javadsl.server.Directives.parameter;

public class HTTPServer {
    private static final String ZERO_COUNT_STRING = "0";

    private Route route (ActorRef actorConfig) {
        return route(
                path( "", () ->
                        get(() ->
                        parameter("url", (url) ->
                                parameter("count", (count) -> {
                                    if (count.equals(ZERO_COUNT_STRING)) {
                                        return Http.get
                                    } else {

                                    }
                                })
                                )
                        )

                )
        );
    }

}
