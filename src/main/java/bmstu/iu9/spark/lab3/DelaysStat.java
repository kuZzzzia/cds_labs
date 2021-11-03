package bmstu.iu9.spark.lab3;

import scala.Serializable;
import scala.Tuple2;

import java.util.Map;

public class DelaysStat implements Serializable {
    private static final int    MIN_FLIGHTS_AMOUNT = 1;
    private static final int    MIN_CANCELLED_FLIGHTS_AMOUNT = 0;
    private static final int    MIN_DELAYED_FLIGHTS_AMOUNT = 0;
    private static final float  NO_DELAY_VALUE = 0.0F;

    private String    departureAirportName;
    private String    destinationAirportName;
    private float     maxDelay;

    private int       flightsCount;
    private int       flightsDelayedCount;
    private int       flightsCancelledCount;


    public DelaysStat(Tuple2<Tuple2<String, String>, DelaysStat> delaysBtwAirports, Map<String, String> airportName) {
        this.departureAirportName = airportName.get(delaysBtwAirports._1()._1());
        this.destinationAirportName = airportName.get(delaysBtwAirports._1()._2());

        DelaysStat delaysStatSrc = delaysBtwAirports._2();

        this.maxDelay = delaysStatSrc.getMaxDelay();
        this.flightsCount = delaysStatSrc.getFlightsCount();
        this.flightsDelayedCount = delaysStatSrc.getFlightsDelayedCount();
        this.flightsCancelledCount = delaysStatSrc.getFlightsCancelledCount();
    }

    public DelaysStat(FlightDelay flightDelay) {
        this.flightsCount = MIN_FLIGHTS_AMOUNT;
        this.flightsCancelledCount = MIN_CANCELLED_FLIGHTS_AMOUNT;
        this.flightsDelayedCount = MIN_DELAYED_FLIGHTS_AMOUNT;

        boolean cancelledStatus = flightDelay.getCancelledStatus();
        if (cancelledStatus) {
            this.maxDelay = NO_DELAY_VALUE;
            this.flightsCancelledCount++;
        } else {
            this.maxDelay = flightDelay.getDelayDuration();
            if (this.maxDelay != NO_DELAY_VALUE) {
                this.flightsDelayedCount++;
            }
        }
    }

    public static DelaysStat addDelay(DelaysStat delayStat, FlightDelay flightDelay) {

    }

    protected float getMaxDelay() {
        return this.maxDelay;
    }

    protected int getFlightsCount() {
        return this.flightsCount;
    }

    protected int getFlightsDelayedCount() {
        return this.flightsDelayedCount;
    }

    protected int getFlightsCancelledCount() {
        return this.flightsCancelledCount;
    }

    @Override
    public String toString() {
        float percentOfCancelledFlights = flightsCancelledCount / flightsCount * 100; //TODO: constant
        float percentOfDelayedFlights = flightsDelayedCount / flightsCount * 100;
        return departureAirportName + " -> " + destinationAirportName +
                "\nMax delay: " + maxDelay +
                "\n" + percentOfCancelledFlights + " flights were cancelled" +
                "\n" + percentOfDelayedFlights + " flights were delayed";
    }

    public static DelaysStat add(DelaysStat a, DelaysStat b) {

    }
}
