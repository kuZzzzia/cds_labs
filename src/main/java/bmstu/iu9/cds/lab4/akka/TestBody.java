package bmstu.iu9.cds.lab4.akka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TestBody {
    private final String testName;
    private final String params;
    private final String expectedResult;

    @JsonCreator
    public TestBody(@JsonProperty("testName") String testName,
                    @JsonProperty("params") String params,
                    @JsonProperty("expectedResult") String expectedResult) {
        this.expectedResult =  expectedResult;
        this.params = params;
        this.testName = testName;
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
