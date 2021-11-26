package bmstu.iu9.cds.lab4.akka;

import akka.actor.AbstractActor;

import java.util.Map;

public class ActorKeeper extends AbstractActor {
    private Map<Integer, TestResult> results;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TestResultMessage.)
    }

    private class TestResult {
        private String status;
        private String testName;
        private String expectedResult;
        private String receivedResult;

        private TestResult() {
            //TODO: implement
        }
    }
}
