package bmstu.hdfs.lab2;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlightMapper extends Mapper<LongWritable, Text, AirportCodeWritableComparable, Text> {
    private static final String separator = ",";

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException, NumberFormatException {
        String[] values = value.toString().split(separator);
        int airportID = Integer.parseInt(values[14]);
        context.write(new AirportCodeWritableComparable(new IntWritable(airportID), ), new Text());
    }
}
