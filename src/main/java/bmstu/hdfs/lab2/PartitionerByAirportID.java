package bmstu.hdfs.lab2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class PartitionerByAirportID extends Partitioner<AirportIDWritableComparable, Text> {

    @Override
    public int getPartition(AirportIDWritableComparable airportIDWritableComparable, Text text, int numReduceTasks) {
        return airportIDWritableComparable.getAirportID();
    }
}
