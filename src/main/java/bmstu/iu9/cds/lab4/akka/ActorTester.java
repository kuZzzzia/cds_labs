package bmstu.iu9.cds.lab4.akka;

import akka.actor.AbstractActor;

public class ActorTester extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        TestBodyMessage.class,
                        m -> {
                            sender().tell(
                                    new StoreTestResultMessage(),
                                    self()
                            );
                        }
                )
                .build();
    }

    private String execJS() {
        
    }

    private StoreTestResultMessage runTest() {

    }
}
