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
    private static final String FLIGHTS_FILE_FIRST_LINE_PREFIX = "\"";
    private static final String AIRPORTS_FILE_FIRST_LINE_PREFIX = "C";
    private static final int    AIRPORT_ID_INDEX = 0;
    private static final int    AIRPORT_NAME_INDEX = 1;


    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName(SPARK_APP_NAME);
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaPairRDD<
                Tuple2<
                        String,
                        String
                        >,
                FlightDelay
                >
                flightsDelays = parseFlightsDelaysFromFile(sc);

        JavaPairRDD<
                Tuple2<
                        String,
                        String
                        >,
                DelaysStat
                >
                delaysStat = flightsDelays.combineByKey(
                        DelaysStat::new,
                        DelaysStat::addDelay,
                        DelaysStat::add
                );

        JavaPairRDD<
                String,
                String
                >
                airportNames = parseAirportsFromFile(sc);

        final Broadcast<Map<String, String>> airportsBroadcast = sc.broadcast(airportNames.collectAsMap());

        JavaRDD<DelaysStat> parsedData = delaysStat.map(
                delaysBtwAirports -> new DelaysStat(
                        delaysBtwAirports,
                        airportsBroadcast.value()
                )
        );

        parsedData.saveAsTextFile(OUTPUT_FILENAME);
    }

    private static JavaPairRDD<Tuple2<String, String>, FlightDelay> parseFlightsDelaysFromFile(JavaSparkContext sc) {
        JavaRDD<String> flights = sc.textFile(HDFS_PATH_TO_FLIGHTS);
        flights = flights.filter(flight -> !flight.startsWith(FLIGHTS_FILE_FIRST_LINE_PREFIX));

        return flights.mapToPair(
                flight -> {
                    String[] flightData = flight.split(DATA_SEPARATOR);
                    return new Tuple2<>(
                            FlightDelay.makePairOfDepartureAndDestinationAirportIDs(flightData),
                            new FlightDelay(flightData)
                    );
                }
                );
    }

    private static JavaPairRDD<String, String> parseAirportsFromFile(JavaSparkContext sc) {
        JavaRDD<String> airports = sc.textFile(HDFS_PATH_TO_AIRPORTS);
        airports = airports.filter(airport -> !airport.startsWith(AIRPORTS_FILE_FIRST_LINE_PREFIX));

        return airports.mapToPair(
                airport -> {
                    String[] airportData = airport.split(DATA_SEPARATOR, 2);
                    return new Tuple2<>(
                            FlightDelay.deleteDoubleQuotes(
                                    airportData[AIRPORT_ID_INDEX]
                            ),
                            FlightDelay.deleteDoubleQuotes(
                                    airportData[AIRPORT_NAME_INDEX]
                            )
                    );
                }
                );
    }

}
