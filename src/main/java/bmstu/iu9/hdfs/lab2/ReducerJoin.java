package bmstu.iu9.hdfs.lab2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ReducerJoin extends Reducer<AirportIDWritableComparable, Text, Text, Text> {
    private static final String floatNumberRegEx = "\\d+\\.\\d+";

    @Override
    protected void reduce(AirportIDWritableComparable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        final Text airportName;
        Iterator<Text> valuesIterator = values.iterator();
        airportName = new Text(valuesIterator.next().toString() + " " + key.getAirportID());
        ArrayList<String> delays = new ArrayList<>();
        while(valuesIterator.hasNext()) {
            String delay = valuesIterator.next().toString();
            if (delay.matches(floatNumberRegEx)) {
                delays.add(delay);
            }
        }
        if (delays.size() > 0) {
            context.write(airportName, computeMinMaxAverageDelay(delays));
        }
    }

    protected Text computeMinMaxAverageDelay(ArrayList<String> delays) {
        float min = Float.MAX_VALUE, max = 0, sum = 0;
        for (String delay: delays) {
            float delayFloatValue = Float.parseFloat(delay);
            if (delayFloatValue < min) {
                min = delayFloatValue;
            }
            if (delayFloatValue > max) {
                max = delayFloatValue;
            }
            sum += delayFloatValue;
        }
        return new Text("min= " + min + ", average= " + sum/delays.size() +  ", max= " + max);
    }
}
