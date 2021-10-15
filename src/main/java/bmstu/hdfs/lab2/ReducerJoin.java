package bmstu.hdfs.lab2;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ReducerJoin extends Reducer<AirportIDWritableComparable, Text, Text, Text> {

    @Override
    protected void reduce(AirportIDWritableComparable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        final Text airportName;
        Iterator<Text> valuesIterator = values.iterator();
        airportName = new Text(valuesIterator.next().toString() + String.valueOf(key.getAirportID()));
        ArrayList<String> delays = new ArrayList<>();
        while(valuesIterator.hasNext()) {
            delays.add(valuesIterator.next().toString());
        }
        if (delays.size() > 0) {
            context.write(airportName, computeMinMaxAverageDelay(delays));
        } else {
            context.write(airportName, new Text("airport"));
        }
    }

    protected Text computeMinMaxAverageDelay(ArrayList<String> delays) {
        float min = Float.MAX_VALUE, max = 0, sum = 0, count = 0;
        for (String delay: delays) {
            try {
                float delayFloatValue = Float.parseFloat(delay);
                if (delayFloatValue < min) {
                    min = delayFloatValue;
                }
                if (delayFloatValue > max) {
                    max = delayFloatValue;
                }
                sum += delayFloatValue;
            } catch (NumberFormatException ignored) {
                count--;
            }
            count++;
        }
        return new Text("min= " + min + ", average= " + sum/count +  ", max= " + max);

    }
}
