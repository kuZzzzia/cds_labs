package bmstu.iu9.hdfs.lab2;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlightJoinMapper extends Mapper<LongWritable, Text, AirportIDWritableComparable, Text> {
    private static final String separator = ",";
    private static final String numberRegEx = "\\d+";
    private static final int destinationAirportIDIndex = 14;
    private static final int delayIndex = 17;
    private static final int datasetIndicator = 1;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException, NumberFormatException {
        String[] values = value.toString().split(separator);
        if (values[destinationAirportIDIndex].matches(numberRegEx)) {
            int airportID = Integer.parseInt(values[destinationAirportIDIndex]);
            String delay = values[delayIndex];
            if (delay.length() != 0) {
                context.write(
                        new AirportIDWritableComparable(
                            new IntWritable(airportID),
                            new IntWritable(datasetIndicator)
                        ),
                        new Text(delay.trim())
                );
            }
        }
    }
}
