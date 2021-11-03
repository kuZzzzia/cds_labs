package bmstu.iu9.spark.lab3;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import scala.Tuple2;

import java.util.Map;

public class AirportAnalyzerApp {
    private static final String SPARK_APP_NAME = "Airport analyzer";
    private static final String HDFS_PATH_TO_FLIGHTS = "flights.csv";
    private static final String HDFS_PATH_TO_AIRPORTS = "airports.csv";
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
                        String,
                        String
                        >,
                FlightDelay
                >
                flightsDelays = flights.mapToPair(
                        flight -> {
                            String[] flightData = flight.split(FLIGHT_DATA_SEPARATOR);
                            return new Tuple2<>(
                                    makePairOfDepartureAndDestinationAirportIDs(flightData),
                                    new FlightDelay(flightData)
                            );
                        }
                        );

        JavaPairRDD<
                Tuple2<
                        String,
                        String
                        >,
                AverageDelayBetweenAirports
                >
                delaysBetweenAirports = flightsDelays.combineByKey();

        JavaRDD<String> airports = sc.textFile(HDFS_PATH_TO_AIRPORTS);
        airports = airports.filter(airport -> !airport.startsWith("C"));

        JavaPairRDD<
                String,
                String
                >
                airportNames = airports.mapToPair(
                        airport -> {
                            String[] airportData = airport.split(FLIGHT_DATA_SEPARATOR, 2);
                            return new Tuple2<>(
                                    deleteDoubleQuotes(airportData[0]),
                                    deleteDoubleQuotes(airportData[1])
                            );
                        }
                        );

        final Broadcast<Map<String, String>> airportsBroadcasted = sc.broadcast(airportNames.collectAsMap());

        JavaPairRDD<ParsedData> parsedData = 


//        flightsDelays.saveAsTextFile(OUTPUT_FILENAME);
    }

    private static String[] getFlightDataBySplittingFlightString(final String flightString) {
        return flightString.split(FLIGHT_DATA_SEPARATOR);
    }

    private static Tuple2<String, String> makePairOfDepartureAndDestinationAirportIDs(final String[] flightData) {
        return new Tuple2<>(
                flightData[DEPARTURE_AIRPORT_ID_INDEX],
                flightData[DESTINATION_AIRPORT_ID_INDEX]
        );
    }

    private static String deleteDoubleQuotes(String s) {
        return s.replaceAll("\"", "");
    }

}
