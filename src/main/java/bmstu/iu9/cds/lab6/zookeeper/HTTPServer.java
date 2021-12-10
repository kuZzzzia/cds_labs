package bmstu.iu9.cds.lab6.zookeeper;

import akka.http.javadsl.server.Route;

import static akka.actor.Nobody.path;
import static akka.http.javadsl.server.Directives.get;
import static akka.http.javadsl.server.Directives.parameter;

public class HTTPServer {

    private Route route () {
        return route(
                path( "", () ->
                        get(() ->
                        parameter("url", (url) ->
                                parameter("count", (count) -> {
                                    
                                })
                                )
                        )

                )
        );
    }

}
