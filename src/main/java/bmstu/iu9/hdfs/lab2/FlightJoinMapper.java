package bmstu.iu9.hdfs.lab2;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlightJoinMapper extends Mapper<LongWritable, Text, AirportIDWritableComparable, Text> {
    private static final String SEPARATOR = ",";
    private static final String CSV_COLUMN_NAME = "\"DEST_AIRPORT_ID\"";
    private static final int DESTINATION_AIRPORT_ID_INDEX = 14;
    private static final int DELAY_INDEX = 17;
    private static final int DATASET_INDICATOR = 1;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException, NumberFormatException {
        String[] values = value
                .toString()
                .split(SEPARATOR);
        String airportIDString = values[DESTINATION_AIRPORT_ID_INDEX];

        if (!airportIDString.equals(CSV_COLUMN_NAME)) {
            int airportID = Integer.parseInt(airportIDString);
            String delay = values[DELAY_INDEX];
            if (delay.length() != 0) {
                context.write(
                        new AirportIDWritableComparable(
                                new IntWritable(airportID),
                                new IntWritable(DATASET_INDICATOR)
                        ),
                        new Text(delay)
                );
            }
        }
    }
}
