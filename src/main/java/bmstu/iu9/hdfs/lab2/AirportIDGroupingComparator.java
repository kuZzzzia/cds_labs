package bmstu.iu9.hdfs.lab2;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class AirportIDGroupingComparator extends WritableComparator {

    public AirportIDGroupingComparator() {
        super(AirportIDWritableComparable.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        AirportIDWritableComparable id1 = (AirportIDWritableComparable) a;
        AirportIDWritableComparable id2 = (AirportIDWritableComparable) b;
        return id1
                .getAirportID()
                .compareTo(id2
                        .getAirportID()
                );
    }
}
