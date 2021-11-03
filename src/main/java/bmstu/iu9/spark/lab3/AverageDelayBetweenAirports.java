package bmstu.iu9.spark.lab3;

import scala.Serializable;

import java.util.Map;

public class AverageDelayBetweenAirports implements Serializable {

    private final String    departureAirportName;
    private final String    destinationAirportName;
    private final int       maxDelay;
    private final float     percentOfCancelledFlights;
    private final float     percentOfDelayedFlights;


    public AverageDelayBetweenAirports(Object delaysBtwAirports, Map<String, String> airportName) {
        // get airports names from map airportName
        // count data
    }

    @Override
    public String toString() {
        return departureAirportName + " -> " + destinationAirportName +
                "\nMax delay: " + maxDelay +
                "\n" + percentOfCancelledFlights + " flights were cancelled" +
                "\n" + percentOfDelayedFlights + " flights were delayed";
    }
}
