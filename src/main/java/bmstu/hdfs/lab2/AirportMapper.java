package bmstu.hdfs.lab2;

import javafx.util.Pair;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AirportMapper extends Mapper<LongWritable, Text, Pair<AirportCodeWritableComparable, Text>, Text> {
    private static final String separator = ",";

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] values = value.toString().split(separator);
        context.write(new Pair<>(new AirportCodeWritableComparable(new IntWritable(), new IntWritable())));
    }
}
