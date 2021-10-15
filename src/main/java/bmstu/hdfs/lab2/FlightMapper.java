package bmstu.hdfs.lab2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Mapper;

import java.io.IOException;

public class FlightMapper extends Mapper<LongWritable, Text, TextPair, Text> {
    @Override
    protected void map(LongWritable key, Text value, org.apache.hadoop.mapreduce.Mapper.Context) throws IOException, InterruptedException {

    }
}
