package bmstu.hdfs.lab2;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ReducerJoin extends Reducer<AirportIDWritableComparable, Text, Text, FloatWritable> {
    
}
