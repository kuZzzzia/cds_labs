package bmstu.hdfs.lab2;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ReduceSideJoinFlightsWithAirportsApp {
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Usage: ReduceSideJoinAirportsWithFlightsApp <inputFlights path> <inputAirports path> <output path>");
            System.exit(-1);
        }
        Job job = Job.getInstance();
        job.setJarByClass(ReduceSideJoinFlightsWithAirportsApp.class);
        job.setJobName("Reduce side join flights with airports");
        MultipleInputs.addInputPath(job, new Path(args[0]), TextInputFormat.class); //TODO: add mapper
        MultipleInputs.addInputPath(job, new Path(args[1]), TextInputFormat.class);

        FileOutputFormat.setOutputPath(job, new Path(args[2]));
        job.setPartitionerClass();
        job.setGroupingComparatorClass();
        job.setReducerClass();
        job.setMapOutputKeyClass();

        job.setOutputKeyClass();
        job.setOutputValueClass();
        job.setNumReduceTasks(2);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
