package bmstu.iu9.cds.lab4.akka;

public class MessageStoreTestResult {
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
