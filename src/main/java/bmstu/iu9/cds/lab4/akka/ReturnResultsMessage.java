package bmstu.iu9.cds.lab4.akka;

import java.util.List;

public class ReturnResultsMessage {
    private final String packageID;
    private final List<TestResult> results;

    public ReturnResultsMessage(String packageID, List<TestResult> results) {
        this.packageID = packageID;
        this.results = results;
    }

    protected String getPackageID() {
        return packageID;
    }

    protected List<TestResult> getResults() {
        return results;
    }

}
