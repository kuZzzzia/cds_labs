package bmstu.iu9.cds.lab4.akka;

public class MessageGetTestPackageResult {
    private final String packageID;

    public MessageGetTestPackageResult(String packageID) {
        this.packageID = packageID;
    }

    protected String getPackageID() {
        return packageID;
    }
}
