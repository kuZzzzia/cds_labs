package bmstu.iu9.cds.lab2.hdfs;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class AirportIDPartitioner extends Partitioner<AirportIDWritableComparable, Text> {

    @Override
    public int getPartition(AirportIDWritableComparable airportIDWritableComparable, Text text, int numReduceTasks) {
        return Math.abs(
                airportIDWritableComparable
                        .getAirportID()
                        .hashCode()
        ) % numReduceTasks;
    }
}
