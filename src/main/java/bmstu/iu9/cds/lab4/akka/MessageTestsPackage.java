package bmstu.iu9.cds.lab4.akka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MessageTestsPackage {
    private final String packageID;
    private final String jsScript;
    private final String funcName;
    private final List<TestBody> tests;

    @JsonCreator
    public MessageTestsPackage(
            @JsonProperty("packageId") String packageID,
            @JsonProperty("jsScript") String jsScript,
            @JsonProperty("funcName") String funcName,
            @JsonProperty("tests") List<TestBody> tests) {
        this.packageID = packageID;
        this.funcName = funcName;
        this.jsScript = jsScript;
        this.tests = tests;
    }

    protected List<TestBody> getTests() {
        return tests;
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
}
