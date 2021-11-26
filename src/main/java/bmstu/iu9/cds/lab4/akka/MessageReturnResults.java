package bmstu.iu9.cds.lab4.akka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MessageReturnResults {
    private final String packageID;
    private final List<TestResult> results;

    @JsonCreator
    public MessageReturnResults(
            @JsonProperty("packageId") String packageID,
            @JsonProperty("results") List<TestResult> results) {
        this.packageID = packageID;
        this.results = results;
    }

    public String getPackageID() {
        return packageID;
    }

    public List<TestResult> getResults() {
        return results;
    }

}
