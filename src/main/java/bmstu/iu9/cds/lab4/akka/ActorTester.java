package bmstu.iu9.cds.lab4.akka;

import akka.actor.AbstractActor;
import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Props;

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

    private final ActorRef storage;

    static Props props(Integer magicNumber) {
        return Props.create(ActorTester.class, () -> new ActorTester(storage));
    }


    public ActorTester(ActorRef storage) {
        this.storage = storage;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        TestBodyMessage.class,
                        m -> storage.tell(
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
            status = TEST_CRASHED_STATUS;
            received = EMPTY_STRING;
        }
        return new StoreTestResultMessage(
                message.getPackageID(),
                status,
                message.getTestName(),
                expected,
                received
        );

    }

    private static boolean isEqual(String expected, String received) {
        return expected.equals(received);
    }
}
