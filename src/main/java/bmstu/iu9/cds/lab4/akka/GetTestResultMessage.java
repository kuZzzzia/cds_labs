package bmstu.iu9.cds.lab4.akka;

public class GetTestResultMessage {
    private final int packageID;

    public GetTestResultMessage(int packageID) {
        this.packageID = packageID;
    }

    protected int getPackageID() {
        return packageID;
    }
}
