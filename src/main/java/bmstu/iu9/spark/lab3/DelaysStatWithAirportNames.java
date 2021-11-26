package bmstu.iu9.spark.lab3;

import scala.Tuple2;

import java.util.Map;

public class DelaysStatWithAirportNames extends DelaysStat {
    private final String      departureAirportName;
    private final String      destinationAirportName;

    public DelaysStatWithAirportNames(Tuple2<String, String> airportnames, DelaysStat delaysStatSrc, Map<String, String> airportName) {
        super(delaysStatSrc.getMaxDelay(), delaysStatSrc.getFlightsCount(), delaysStatSrc.getDelayedCount(), delaysStatSrc.getCancelledCount());

        Tuple2<String, String> airportNames = delaysBtwAirports._1();
        this.departureAirportName = airportName.get(airportNames._1());
        this.destinationAirportName = airportName.get(airportNames._2());
    }
}
