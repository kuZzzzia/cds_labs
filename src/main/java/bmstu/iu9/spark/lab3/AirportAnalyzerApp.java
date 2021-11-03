package bmstu.iu9.spark.lab3;

import org.apache.spark.SparkConf;

public class AirportAnalyzerApp {
    private static final String SPARK_APP_NAME = "Airport analyzer";

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName(SPARK_APP_NAME);
        
    }
}
