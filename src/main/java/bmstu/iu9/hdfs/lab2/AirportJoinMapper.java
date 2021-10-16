package bmstu.iu9.hdfs.lab2;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AirportJoinMapper extends Mapper<LongWritable, Text, AirportIDWritableComparable, Text> {
    private static final String SEPARATOR = ",";
    private static final String EMPTY_STRING = "";
    private static final String DOUBLE_QUOTES_REG_EX = "\"";
    private static final String CSV_COLUMN_NAME = "Code";
    private static final int LIMIT_SEPARATOR = 2;
    private static final int AIRPORT_ID_INDEX = 0;
    private static final int AIRPORT_NAME_INDEX = 1;
    private static final int DATASET_INDICATOR = 0;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException, NumberFormatException {
        String[] values = value.toString().split(SEPARATOR, LIMIT_SEPARATOR);

        String airportIdString = removeDoubleQuotesFromString(values[AIRPORT_ID_INDEX]);
        String airportName = removeDoubleQuotesFromString(values[AIRPORT_NAME_INDEX]);
        if (!airportIdString.equals(CSV_COLUMN_NAME)) {
            int airportID = Integer.parseInt(airportIdString);
            context.write(
                    new AirportIDWritableComparable(
                            new IntWritable(airportID),
                            new IntWritable(DATASET_INDICATOR)
                    ),
                    new Text(airportName)
            );
        }
    }

    private static String removeDoubleQuotesFromString(final String data) {
        return data
                .replaceAll(DOUBLE_QUOTES_REG_EX, EMPTY_STRING);
    }
}
