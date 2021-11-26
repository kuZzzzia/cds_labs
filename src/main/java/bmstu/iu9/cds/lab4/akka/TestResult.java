package bmstu.iu9.cds.lab4.akka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TestResult {
    private final String status;
    private final String testName;
    private final String expectedResult;
    private final String receivedResult;

    @JsonCreator
    public TestResult(@JsonProperty("status") String status,
                      @JsonProperty("testName") String testName,
                      @JsonProperty("expectedResult") String expectedResult,
                      @JsonProperty("receivedResult") String receivedResult) {
        this.status = status;
        this.testName = testName;
        this.expectedResult = expectedResult;
        this.receivedResult = receivedResult;
    }

    protected String getStatus() {
        return status;
    }

    protected String getTestName() {
        return testName;
    }

    protected String getExpectedResult() {
        return expectedResult;
    }

    protected String getReceivedResult() {
        return receivedResult;
    }

    @Override
    public String toString() {
        return "Status: " + status + "\n"
                + "Test name: " + testName + "\n"
                + "Expected: " + expectedResult + "\n"
                + "Received: " + receivedResult + "\n";
    }

}