package bmstu.hdfs.lab2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class PartitionerByAirportID extends Partitioner<AirportIDWritableComparable, Text> {
    public int getPartition()

    @Override
    public int getPartition(AirportIDWritableComparable airportIDWritableComparable, Text text, int i) {
        return 0;
    }
}
