package bmstu.iu9.hdfs.lab2;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AirportJoinMapper extends Mapper<LongWritable, Text, AirportIDWritableComparable, Text> {
    private static final String SEPARATOR = ",";
    private static final String WHITESPACE_REG_EX = "\\s";
    private static final String EMPTY_STRING = "";
    private static final String DOUBLE_QUOTES_REG_EX = "\"";
    private static final String NUMBER_REG_EX = "\\d+";
    private static final int DATASET_INDICATOR = 0;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException, NumberFormatException {
        String stringValue = value.toString();
        String airportIdCandidate = ;
        String airportName = stringValue.substring(indexOfSeparator + 1).replaceAll(DOUBLE_QUOTES_REG_EX, EMPTY_STRING).trim();
        if (airportIdCandidate.matches(NUMBER_REG_EX)) {
            int airportID = Integer.parseInt(airportIdCandidate);
            context.write(new AirportIDWritableComparable(new IntWritable(airportID), new IntWritable(DATASET_INDICATOR)),
                    new Text(airportName));
        }
    }

    private String getAirportID(String data, int index) {
        final int indexOfSeparator = stringValue.indexOf(SEPARATOR);
        return data.substring(0, indexOfSeparator).replaceAll(WHITESPACE_REG_EX, EMPTY_STRING)
                .replaceAll(DOUBLE_QUOTES_REG_EX, EMPTY_STRING)
    }
}
