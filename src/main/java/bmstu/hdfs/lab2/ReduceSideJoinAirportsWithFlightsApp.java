package bmstu.hdfs.lab2;
import org.apache.hadoop.mapreduce.Job;

public class ReduceSideJoinAirportsWithFlightsApp {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: ReduceSideJoinAirportsWithFlightsApp <input path> <output path>");
            System.exit(-1);
        }
        Job job = Job.getInsatnce();
    }
}
