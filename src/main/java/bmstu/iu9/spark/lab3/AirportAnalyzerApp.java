package bmstu.iu9.spark.lab3;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

public class AirportAnalyzerApp {
    private static final String SPARK_APP_NAME = "Airport analyzer";
    private static final String HDFS_PATH_TO_FLIGHTS = "flights.csv";
    private static final String FLIGHT_DATA_SEPARATOR = ",";

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName(SPARK_APP_NAME);
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> flights = sc.textFile(HDFS_PATH_TO_FLIGHTS);
        flights = flights.filter(flight -> !flight.startsWith("\""));

        JavaPairRDD<
                Tuple2<
                        Integer,
                        Integer
                        >,
                Float
                > flightsData = flights.mapToPair(
                        flight ->
                                new Tuple2<>(
                                        new Tuple2<>(
                                                getAirportID(flight, index),
                                                getAirportID(flight, index)
                                        ),

                                        )
        );

        //saveAsTextFile() - method
    }

    private static String[] getFlightDataBySplittingFlightString(final String flightString) {
        return flightString.split(FLIGHT_DATA_SEPARATOR);
    }

    private static Tuple2<Integer, Integer> makePairOfDepartureAndDestinationAirportIDs(final String[] flightData, int departureIndex, int destinationIndex) {
        return new Tuple2<>(
                new Integer(
                        flightData[departureIndex]
                ),
                new Integer(
                        flightData[destinationIndex]
                )
        );
    }
}
