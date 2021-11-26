package bmstu.iu9.cds.lab4.akka;

public class GetTestPackageResultMessage {
    private final String packageID;

    public GetTestPackageResultMessage(String packageID) {
        this.packageID = packageID;
    }

    protected String getPackageID() {
        return packageID;
    }
}
