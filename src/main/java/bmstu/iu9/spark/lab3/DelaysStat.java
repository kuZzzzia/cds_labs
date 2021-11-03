package bmstu.iu9.spark.lab3;

import scala.Serializable;
import scala.Tuple2;

import java.util.Map;

public class DelaysStat implements Serializable {

    private String    departureAirportName;
    private String    destinationAirportName;
    private int       maxDelay;

    private int       flightsCount;
    private int       flightsDelayedCount;
    private int       flightsCancelledCount;


    public DelaysStat(Tuple2<Tuple2<String, String>, DelaysStat> delaysBtwAirports, Map<String, String> airportName) {
        this.departureAirportName = airportName.get(delaysBtwAirports._1._1);
        this.destinationAirportName = airportName.get(delaysBtwAirports._1._2);

        DelaysStat delaysStatSrc = delaysBtwAirports._2();

        this.maxDelay = delaysStatSrc.maxDelay;
        this.flightsCount = delaysStatSrc.flightsCount;
        this.flightsDelayedCount = delaysStatSrc.flightsDelayedCount;
        this.flightsCancelledCount = delaysStatSrc.flightsCancelledCount;
    }
    
    public DelaysStat(DelaysStat delaysStat) {

    }

    @Override
    public String toString() {
        float percentOfCancelledFlights = flightsCancelledCount / flightsCount * 100;
        float percentOfDelayedFlights = flightsDelayedCount / flightsCount * 100;
        return departureAirportName + " -> " + destinationAirportName +
                "\nMax delay: " + maxDelay +
                "\n" + percentOfCancelledFlights + " flights were cancelled" +
                "\n" + percentOfDelayedFlights + " flights were delayed";
    }
}
