package bmstu.iu9.cds.lab4.akka;

import akka.actor.ActorSystem;

public class JSTestApp {
    private static final String ACTOR_SYSTEM_NAME = "js test app";

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create(ACTOR_SYSTEM_NAME);
    }

}
