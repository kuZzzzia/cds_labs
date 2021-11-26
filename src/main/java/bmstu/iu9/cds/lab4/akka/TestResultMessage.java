package bmstu.iu9.cds.lab4.akka;

public class TestResultMessage {

    private int    packageId;
    private String status;
    private String testName;
    private String expectedResult;
    private String receivedResult;

    public TestResultMessage(int packageId, String status,
                             String testName, String expectedResult, String receivedResult) {
        this.packageId = packageId;
        this.status = status;
        this.testName = testName;
        this.expectedResult = expectedResult;
        this.receivedResult = receivedResult;
    }

    protected int getPackageId() {
        return packageId;
    }


}
