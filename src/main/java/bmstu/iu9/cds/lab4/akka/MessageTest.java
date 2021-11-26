package bmstu.iu9.cds.lab4.akka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageTest {
    private final String packageID;
    private final String jsScript;
    private final String funcName;
    private final TestBody test;
    @JsonCreator
    public MessageTest(@JsonProperty("packageId") String packageID,
                       @JsonProperty("jsScript") String jsScript,
                       @JsonProperty("functionName") String funcName,
                       @JsonProperty("test") TestBody test) {
        this.packageID = packageID;
        this.funcName = funcName;
        this.jsScript = jsScript;
        this.test = test;
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

    protected TestBody getTest() {
        return test;
    }
}
