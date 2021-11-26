package bmstu.iu9.cds.lab4.akka;

public class GetTestResultMessage {
    private final String packageID;

    public GetTestResultMessage(String packageID) {
        this.packageID = packageID;
    }

    protected String getPackageID() {
        return packageID;
    }
}
