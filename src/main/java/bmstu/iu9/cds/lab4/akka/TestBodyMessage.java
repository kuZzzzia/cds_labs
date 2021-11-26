package bmstu.iu9.cds.lab4.akka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TestBodyMessage {
    private final String packageID;
    private final String jsScript;
    private final String funcName;
    private final String testName;
    private final String params;
    private final String expectedResult;

    @JsonCreator
    public TestBodyMessage(
            @JsonProperty("packageId") String packageID,
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

    protected String getPackageID() {
        return packageID;
    }

    protected String getJsScript() {
        return jsScript;
    }

    protected String getFuncName() {
        return funcName;
    }

    protected String getTestName() {
        return testName;
    }

    protected String getParams() {
        return params;
    }

    protected String getExpectedResult() {
        return expectedResult;
    }


}
