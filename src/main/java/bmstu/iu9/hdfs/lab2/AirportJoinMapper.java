package bmstu.iu9.hdfs.lab2;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AirportJoinMapper extends Mapper<LongWritable, Text, AirportIDWritableComparable, Text> {
    private static final String separator = ",";
    private static final String whitespaceRegEx = "\\s";
    private static final String emptyString = "";
    private static final String doubleQuotesRegEx = "\"";
    private static final String numberRegEx = "\\d+";
    private static final int datasetIndicator = 0;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException, NumberFormatException {
        String stringValue = value.toString();
        final int indexOfSeparator = stringValue.indexOf(separator);
        String airportIdCandidate = stringValue.substring(0, indexOfSeparator).replaceAll(whitespaceRegEx, emptyString)
                .replaceAll(doubleQuotesRegEx, emptyString);
        String airportName = stringValue.substring(indexOfSeparator + 1).replaceAll(doubleQuotesRegEx, emptyString).trim();
        if (airportIdCandidate.matches(numberRegEx)) {
            int airportID = Integer.parseInt(airportIdCandidate);
            context.write(new AirportIDWritableComparable(new IntWritable(airportID), new IntWritable(datasetIndicator)),
                    new Text(airportName));
        }
    }
}
