package bmstu.iu9.cds.lab4.akka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageStoreTestResult {
    private final String    packageId;
    private final String    result;

    @JsonCreator
    public MessageStoreTestResult(@JsonProperty("packageId") String packageId, @JsonProperty("status") String status,
                                  String testName, String expectedResult, String receivedResult) {
        this.packageId = packageId;
        this.result = "Status: " + status;
    }

    protected String getPackageId() {
        return packageId;
    }

    protected String getTestResult() {
        return result;
    }
}
