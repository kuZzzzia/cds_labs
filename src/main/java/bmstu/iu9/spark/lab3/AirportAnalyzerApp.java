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
    private static final String OUTPUT_FILENAME = "delays";
    private static final int    DEPARTURE_AIRPORT_ID_INDEX = 11;
    private static final int    DESTINATION_AIRPORT_ID_INDEX = 14;


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
                FlightDelay
                > flightsDelays = flights.mapToPair(
                        flight -> {
                            String[] flightData = getFlightDataBySplittingFlightString(flight);
                            return new Tuple2<>(
                                    makePairOfDepartureAndDestinationAirportIDs(flightData),
                                    new FlightDelay(flightData)
                            );
                        }
        );

        flightsDelays.saveAsTextFile(OUTPUT_FILENAME);
    }

    private static String[] getFlightDataBySplittingFlightString(final String flightString) {
        return flightString.split(FLIGHT_DATA_SEPARATOR);
    }

    private static Tuple2<Integer, Integer> makePairOfDepartureAndDestinationAirportIDs(final String[] flightData) {
        return new Tuple2<>(
                Integer.valueOf(
                        flightData[DEPARTURE_AIRPORT_ID_INDEX]
                ),
                Integer.valueOf(
                        flightData[DESTINATION_AIRPORT_ID_INDEX]
                )
        );
    }

}
