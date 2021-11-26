package bmstu.iu9.cds.lab4.akka;

public class MessageStoreTestResult {
    private static final String NEW_LINE_CHARACTER = "\n";

    private final String    packageId;
    private final String    result;

    public MessageStoreTestResult(String packageId, String status,
                                  String testName, String expectedResult, String receivedResult) {
        this.packageId = packageId;
        this.result = "Status: " + status + NEW_LINE_CHARACTER
                + "TestName: " + testName + NEW_LINE_CHARACTER
                + "ExpectedResult: " + expectedResult + NEW_LINE_CHARACTER
                + "ReceivedResult: " + receivedResult + NEW_LINE_CHARACTER;
    }

    protected String getPackageId() {
        return packageId;
    }

    protected String getTestResult() {
        return result;
    }

    @Override
    public String toString() {
        return "PackageId: " + packageId + NEW_LINE_CHARACTER + result;
    }
}
