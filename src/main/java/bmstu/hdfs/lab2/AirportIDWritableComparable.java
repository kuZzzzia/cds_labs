package bmstu.hdfs.lab2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class AirportIDWritableComparable implements WritableComparable<AirportIDWritableComparable> {
    private final IntWritable airportID;
    private final IntWritable datasetIndicator;

    public AirportIDWritableComparable(IntWritable airportID, IntWritable datasetIndicator) {
        this.airportID = airportID;
        this.datasetIndicator = datasetIndicator;
    }

    protected int getAirportID() {
        return this.airportID.get();
    }

    @Override
    public int compareTo(AirportIDWritableComparable o) {
        int thisID = this.getAirportID();
        int thatID = o.getAirportID();
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
