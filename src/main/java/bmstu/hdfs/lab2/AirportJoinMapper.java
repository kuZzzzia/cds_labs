package bmstu.hdfs.lab2;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AirportJoinMapper extends Mapper<LongWritable, Text, AirportIDWritableComparable, Text> {
    private static final String separator = ",";
    private static final String whitespaceRegEx = "\\s";
    private static final String emptyString = "";
    private static final String doubleQuotesRegEx = "\"";

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException, NumberFormatException {
        final int indexOfSeparator = value.toString().indexOf(separator);
        String airportIdCandidate = value.toString().substring(1, indexOfSeparator - 1).replaceAll(whitespaceRegEx, emptyString).replaceAll(doubleQuotesRegEx, emptyString);
        String airportName = value.toString().substring(indexOfSeparator + 1).replaceAll(whitespaceRegEx, emptyString).replaceAll(doubleQuotesRegEx, emptyString);
        try {
            int airportID = Integer.parseInt(airportIdCandidate);
            context.write(new AirportIDWritableComparable(new IntWritable(airportID), new IntWritable(0)), new Text(airportName));
        } catch (NumberFormatException ignored) {}
    }
}
