package bmstu.iu9.cds.lab4.akka;

import akka.actor.AbstractActor;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ActorTester extends AbstractActor {
    private static final String SCRIPT_ENGINE_NAME = "nashorn";
    private static final String TEST_PASSED_STATUS = "PASSED";
    private static final String TEST_FAILED_STATUS = "FAILED";
    private static final String TEST_CRASHED_STATUS = "CRASHED";
    private static final String EMPTY_STRING = "";

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        ActorRouter.MessageTest.class,
                        m -> sender().tell(
                                runTest(m),
                                self()
                        )
                )
                .build();
    }

    private String execJS(String jscript, String functionName, int[] params) throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName(SCRIPT_ENGINE_NAME);
        engine.eval(jscript);
        Invocable invocable = (Invocable) engine;
        return invocable.invokeFunction(functionName,params[0], params[1]).toString();
    }

    private MessageStoreTestResult runTest(ActorRouter.MessageTest message) {
        String received;
        String status;
        String expected = message.getTest().getExpectedResult();
        try {
            received = execJS(
                    message.getJsScript(),
                    message.getFuncName(),
                    message.getTest().getParams()
            );
            status = isEqual(received, expected) ? TEST_PASSED_STATUS : TEST_FAILED_STATUS;
        } catch (ScriptException | NoSuchMethodException e) {
            status = TEST_CRASHED_STATUS;
            received = EMPTY_STRING;
        }
        return new MessageStoreTestResult(
                message.getPackageID(),
                status,
                message.getTest().getTestName(),
                expected,
                received
        );

    }

    private static boolean isEqual(String expected, String received) {
        return expected.equals(received);
    }

    static class MessageStoreTestResult {
        private static final String NEW_LINE_CHARACTER = "\n";

        private final String     packageId;
        private final TestResult result;

        public MessageStoreTestResult(String packageId, String status,
                                      String testName, String expectedResult, String receivedResult) {
            this.packageId = packageId;
            this.result = new TestResult(status, testName, expectedResult, receivedResult);
        }

        protected String getPackageId() {
            return packageId;
        }

        protected TestResult getTestResult() {
            return result;
        }

        @Override
        public String toString() {
            return "PackageId: " + packageId + NEW_LINE_CHARACTER + result;
        }
    }
}
