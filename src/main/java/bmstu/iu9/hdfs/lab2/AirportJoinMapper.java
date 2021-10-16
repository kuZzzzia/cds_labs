package bmstu.iu9.hdfs.lab2;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AirportJoinMapper extends Mapper<LongWritable, Text, AirportIDWritableComparable, Text> {
    private static final String SEPARATOR = ",";
    private static final String WHITESPACE_REG_EX = "\\s";
    private static final String EMPTY_STRING = "";
    private static final String DOUBLE_QUOTES_REG_EX = "\"";
    private static final String INTEGER_REG_EX = "^\\d+$";
    private static final int LIMIT_SEPARATOR = 2;
    private static final int DATASET_INDICATOR = 0;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException, NumberFormatException {
        String[] values = value.toString().split(SEPARATOR, LIMIT_SEPARATOR);
        String stringValue = value.toString();
        final int indexOfSeparator = stringValue.indexOf(SEPARATOR);
        String airportIdCandidate = getAirportID(stringValue, indexOfSeparator);
        String airportName = getAirportName(stringValue, indexOfSeparator);
        if (airportIdCandidate.matches(INTEGER_REG_EX)) {
            int airportID = Integer.parseInt(airportIdCandidate);
            context.write(
                    new AirportIDWritableComparable(
                            new IntWritable(airportID),
                            new IntWritable(DATASET_INDICATOR)
                    ),
                    new Text(airportName)
            );
        }
    }

    private static String getAirportID(final String data, final int indexOfSeparator) {
        return data
                .substring(0, indexOfSeparator)
                .replaceAll(WHITESPACE_REG_EX, EMPTY_STRING)
                .replaceAll(DOUBLE_QUOTES_REG_EX, EMPTY_STRING);
    }

    private static String getAirportName(final String data, final int indexOfSeparator) {
        return data
                .substring(indexOfSeparator + 1)
                .replaceAll(DOUBLE_QUOTES_REG_EX, EMPTY_STRING)
                .trim();
    }
}
