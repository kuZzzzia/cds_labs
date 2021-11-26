package bmstu.iu9.cds.lab4.akka;

public class MessageStoreTestResult {
    private final String        packageId;
    private final TestResult    result;

    public MessageStoreTestResult(String packageId, String status,
                                  String testName, String expectedResult, String receivedResult) {
        result = new TestResult(status, testName, expectedResult, receivedResult);
        this.packageId = packageId;
    }

    protected String getPackageId() {
        return packageId;
    }

    protected  TestResult getTestResult() {
        return result;
    }
}
