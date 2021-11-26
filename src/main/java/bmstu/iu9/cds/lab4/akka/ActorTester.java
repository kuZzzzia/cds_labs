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
    private static final String TEST__STATUS

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        TestBodyMessage.class,
                        m -> sender().tell(
                                runTest(m),
                                self()
                        )
                )
                .build();
    }

    private String execJS(String jscript, String functionName, String params) throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName(SCRIPT_ENGINE_NAME);
        engine.eval(jscript);
        Invocable invocable = (Invocable) engine;
        return invocable.invokeFunction(functionName, params).toString();
    }

    private StoreTestResultMessage runTest(TestBodyMessage message) {
        String received;
        String status;
        String expected = message.getExpectedResult();
        try {
            received = execJS(
                    message.getJsScript(),
                    message.getFuncName(),
                    message.getParams()
            );
            status = isEqual(received, expected) ? TEST_PASSED_STATUS : TEST_FAILED_STATUS;
        } catch (ScriptException | NoSuchMethodException e) {

        }


    }

    private static boolean isEqual(String expected, String received) {
        return expected.equals(received);
    }
}
