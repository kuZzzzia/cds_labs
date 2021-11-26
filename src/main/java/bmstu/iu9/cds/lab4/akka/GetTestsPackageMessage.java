package bmstu.iu9.cds.lab4.akka;

import java.util.List;

public class GetTestsPackageMessage {
    private int packageID;
    private List<TestResult> results;

    public GetTestsPackageMessage(int packageID, List<TestResult> results) {
        this.packageID = packageID;
        this.results = results;
    }

}
