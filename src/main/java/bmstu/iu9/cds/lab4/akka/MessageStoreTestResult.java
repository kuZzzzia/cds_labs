package bmstu.iu9.cds.lab4.akka;

public class MessageStoreTestResult {
    private final String    packageId;
    private final String    result;

    public MessageStoreTestResult(String packageId, String status,
                                  String testName, String expectedResult, String receivedResult) {
        this.packageId = packageId;
        this.result = "Status: " + status;
    }

    protected String getPackageId() {
        return packageId;
    }

    protected String getTestResult() {
        return result;
    }
}
