package bmstu.hdfs.lab2;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AirportJoinMapper extends Mapper<LongWritable, Text, AirportIDWritableComparable, Text> {
    private static final String separator = ",";

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException, NumberFormatException {
        final int indexOfSeparator = value.toString().indexOf(separator);
        String airportIdCandidate = value.toString().substring(1, indexOfSeparator - 1);
        String airportNameCandidate = value.toString().substring(indexOfSeparator)
        try {
            int airportID = Integer.parseInt(values[0].substring(1, values[0].length() - 1));
            context.write(new AirportIDWritableComparable(new IntWritable(airportID), new IntWritable(0)), new Text(values[1]));
        } catch (NumberFormatException ignored) {}
    }
}
