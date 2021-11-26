package bmstu.iu9.cds.lab4.akka;

public class TestResult {
    private String status;
    private String testName;
    private String expectedResult;
    private String receivedResult;

    public TestResult(String status, String testName,
                      String expectedResult, String receivedResult) {
        this.status = status;
        this.testName = testName;
        this.expectedResult = expectedResult;
        this.receivedResult = receivedResult;
    }

}
