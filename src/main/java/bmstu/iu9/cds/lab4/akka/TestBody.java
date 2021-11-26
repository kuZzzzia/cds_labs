package bmstu.iu9.cds.lab4.akka;

public class TestBody {
    private final int packageID;
    private final String jsScript;
    private final String funcName;
    private final String testName;
    private final String params;
    private final String expectedResult;

    public TestBody(int packageID,
            String jsScript,
            String funcName,
            String testName,
            String params,
            String expectedResult) {
        this.packageID = packageID;
        this.funcName = funcName;
        this.testName = testName;
        this.jsScript = jsScript;
        this.params = params;
        this.expectedResult = expectedResult;
    }

}
