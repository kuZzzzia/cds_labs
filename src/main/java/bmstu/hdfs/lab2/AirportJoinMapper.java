package bmstu.hdfs.lab2;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AirportJoinMapper extends Mapper<LongWritable, Text, AirportIDWritableComparable, Text> {
    private static final String separator = ",";

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException, NumberFormatException {
        String[] values = value.toString().split(separator);
        int airportID = Integer.parseInt(values[0]);
        context.write(new AirportIDWritableComparable(new IntWritable(airportID), new IntWritable(0)), new Text(values[1]));
    }
}
