package bmstu.hdfs.lab2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class AirportCodeWritableComparable implements WritableComparable {
    private final IntWritable airportID;
    private final IntWritable datasetIndicator;

    public AirportCodeWritableComparable() {
        airportID = new IntWritable(0);
        datasetIndicator = new IntWritable(0);
    }

    public AirportCodeWritableComparable(IntWritable airportID, IntWritable datasetIndicator) {
        this.airportID = airportID;
        this.datasetIndicator = datasetIndicator;
    }

    private int getAirportID() {
        return this.airportID.get();
    }

    @Override
    public int compareTo(Object o) {
        int thisID = this.getAirportID();
        int thatID = ((AirportCodeWritableComparable) o).getAirportID();
        return Integer.compare(thisID, thatID);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        airportID.write(dataOutput);
        datasetIndicator.write(dataOutput);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        airportID.readFields(dataInput);
        datasetIndicator.readFields(dataInput);
    }
}
