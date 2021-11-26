package bmstu.iu9.cds.lab4.akka;

import akka.actor.AbstractActor;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Arrays;
import java.util.Collections;

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
                        MessageTest.class,
                        m -> sender().tell(
                                runTest(m),
                                self()
                        )
                )
                .build();
    }

    private String execJS(String jscript, String functionName, Object... params) throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName(SCRIPT_ENGINE_NAME);
        engine.eval(jscript);
        Invocable invocable = (Invocable) engine;
        return invocable.invokeFunction(functionName, Arrays.toString(params)).toString();
    }

    private MessageStoreTestResult runTest(MessageTest message) {
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
}
