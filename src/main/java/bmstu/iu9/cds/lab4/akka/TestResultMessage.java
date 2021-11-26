package bmstu.iu9.cds.lab4.akka;

public class TestResultMessage {
    private final int           packageId;
    private final TestResult    result;

    public TestResultMessage(int packageId, String status,
                             String testName, String expectedResult, String receivedResult) {
        result = new TestResult(status, testName, expectedResult, receivedResult);
        this.packageId = packageId;
    }

    protected int getPackageId() {
        return packageId;
    }

    protected  TestResult getTestResult() {
        return result;
    }


}
