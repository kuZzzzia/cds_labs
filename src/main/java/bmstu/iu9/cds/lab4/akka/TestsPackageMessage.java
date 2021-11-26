package bmstu.iu9.cds.lab4.akka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TestsPackageMessage {
    private final String packageID;
    private final String jsScript;
    private final String funcName;
    private final List<TestBody> tests;

    @JsonCreator
    public TestsPackageMessage(
            @JsonProperty("packageId") String packageID,
            @JsonProperty("jsScript") String jsScript,
            @JsonProperty("funcName") String funcName,
            @JsonProperty("testName") String testName,
            @JsonProperty("params") String params,
            @JsonProperty("expectedResult") String expectedResult) {
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

    private static class TestBody {

    }
}
