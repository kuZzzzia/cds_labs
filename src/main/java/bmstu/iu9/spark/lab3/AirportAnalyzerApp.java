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
    private static final String DATA_SEPARATOR = ",";
    private static final String OUTPUT_FILENAME = "delays";


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
                            String[] flightData = flight.split(DATA_SEPARATOR);
                            FlightDelay flightDelay = new FlightDelay(flightData);
                            return new Tuple2<>(
                                    flightDelay.makePairOfDepartureAndDestinationAirportIDs(),
                                    flightDelay
                            );
                        }
                        );

        JavaPairRDD<
                Tuple2<
                        String,
                        String
                        >,
                DelaysStat
                >
                averageDelaysBetweenAirports = flightsDelays.combineByKey(
                        flightDelay -> new DelaysStat(flightDelay),
                        ()
                );


        JavaRDD<String> airports = sc.textFile(HDFS_PATH_TO_AIRPORTS);
        airports = airports.filter(airport -> !airport.startsWith("C"));

        JavaPairRDD<
                String,
                String
                >
                airportNames = airports.mapToPair(
                        airport -> {
                            String[] airportData = airport.split(DATA_SEPARATOR, 2);
                            return new Tuple2<>(
                                    deleteDoubleQuotes(
                                            airportData[0]
                                    ),
                                    deleteDoubleQuotes(
                                            airportData[1]
                                    )
                            );
                        }
                        );

        final Broadcast<Map<String, String>> airportsBroadcast = sc.broadcast(airportNames.collectAsMap());

        JavaRDD<DelaysStat> parsedData = averageDelaysBetweenAirports.map(
                delaysBtwAirports -> new DelaysStat(delaysBtwAirports, airportsBroadcast.value())
        );


        parsedData.saveAsTextFile(OUTPUT_FILENAME);
    }

    private static String deleteDoubleQuotes(String s) {
        return s.replaceAll("\"", "");
    }

}
