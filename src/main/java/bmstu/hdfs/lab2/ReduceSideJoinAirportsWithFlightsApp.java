package bmstu.hdfs.lab2;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ReduceSideJoinAirportsWithFlightsApp {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: ReduceSideJoinAirportsWithFlightsApp <input path> <output path>");
            System.exit(-1);
        }
        Job job = Job.getInstance();
        job.setJarByClass(ReduceSideJoinAirportsWithFlightsApp.class);
        job.setJobName("Reduce side join airports with flights");
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
    }
}
