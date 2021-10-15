package bmstu.hdfs.lab2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class AirportCodeWritableComparable implements WritableComparable {
    private int airportID;
    private int datasetIndicator;

    public AirportCodeWritableComparable() {
        airportID = 0;
        datasetIndicator = 0;
    }

    public AirportCodeWritableComparable(int airportID, int datasetIndicator) {
        this.airportID = airportID;
        this.datasetIndicator = datasetIndicator;
    }

    private int getAirportID() {
        return this.airportID;
    }

    private int getDatasetIndicator() {
        return  this.datasetIndicator;
    }

    private void setAirportID(int airportID) {
        this.airportID = airportID;
    }
    private void setDatasetIndicator(int datasetIndicator) {
        this.datasetIndicator = datasetIndicator;
    }

    @Override
    public int compareTo(Object o) {
        int thisID = this.getAirportID();
        int thatID = ((AirportCodeWritableComparable) o).getAirportID();
        return Integer.compare(thisID, thatID);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(getAirportID());
        dataOutput.writeInt(getDatasetIndicator());
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        setAirportID(dataInput.readInt());
        setDatasetIndicator(dataInput.readInt());
    }
}
