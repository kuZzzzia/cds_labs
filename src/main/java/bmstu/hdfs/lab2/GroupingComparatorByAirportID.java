package bmstu.hdfs.lab2;

import org.apache.hadoop.io.WritableComparator;

public class GroupingComparatorByAirportID extends WritableComparator {

    public GroupingComparatorByAirportID() {
        super(AirportIDWritableComparable.class, true);
    }

    @Override
    public int compare() {

    }
}
