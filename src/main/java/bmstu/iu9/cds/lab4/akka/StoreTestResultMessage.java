package bmstu.iu9.cds.lab4.akka;

public class StoreTestResultMessage {
    private final int           packageId;
    private final TestResult    result;

    public StoreTestResultMessage(int packageId, String status,
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

    @Override
    public String toString() {
        return "PackageID = " + packageId + "\n"
                + result;
    }


}
