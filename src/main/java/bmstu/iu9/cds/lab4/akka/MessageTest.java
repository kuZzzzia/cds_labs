package bmstu.iu9.cds.lab4.akka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageTest {
    private final String packageID;
    private final String jsScript;
    private final String funcName;
    private final String testName;
    private final String params;
    private final String expectedResult;

    @JsonCreator
    public MessageTest(@JsonProperty("packageId") String packageID,
                       @JsonProperty("jsScript") String jsScript,
                       @JsonProperty("funcName") String funcName,
                       @JsonProperty("testName") String testName,
                       @JsonProperty("params") String params,
                       @JsonProperty("expectedResult") String expectedResult) {
        this.packageID = packageID;
        this.funcName = funcName;
        this.jsScript = jsScript;
        this.expectedResult =  expectedResult;
        this.params = params;
        this.testName = testName;
    }
}
