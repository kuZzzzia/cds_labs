package bmstu.iu9.cds.lab4.akka;

public class TestResult {
    private final String status;
    private final String testName;
    private final String expectedResult;
    private final String receivedResult;

    public TestResult(String status, String testName,
                      String expectedResult, String receivedResult) {
        this.status = status;
        this.testName = testName;
        this.expectedResult = expectedResult;
        this.receivedResult = receivedResult;
    }

    @Override
    public String toString() {
        return "Status: " + status + "\n"
                + "Test name: " + testName + "\n"
                + "Expected: " + expectedResult + "\n"
                + "Received: " + receivedResult + "\n";
    }

}
