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

    private int       delayedCount;
    private int       cancelledCount;

    private final float     maxDelay;
    private final int       flightsCount;

    public DelaysStat(Tuple2<Tuple2<String, String>, DelaysStat> delaysBtwAirports, Map<String, String> airportName) {
        this.departureAirportName = airportName.get(delaysBtwAirports._1()._1());
        this.destinationAirportName = airportName.get(delaysBtwAirports._1()._2());

        DelaysStat delaysStatSrc = delaysBtwAirports._2();

        this.maxDelay = delaysStatSrc.getMaxDelay();
        this.flightsCount = delaysStatSrc.getFlightsCount();
        this.delayedCount = delaysStatSrc.getDelayedCount();
        this.cancelledCount = delaysStatSrc.getCancelledCount();
    }

    public DelaysStat(float maxDelay, int flightsCount, int delayedCount, int cancelledCount) {
        this.maxDelay = maxDelay;
        this.flightsCount = flightsCount;
        this.delayedCount = delayedCount;
        this.cancelledCount = cancelledCount;
    }

    public DelaysStat(FlightDelay flightDelay) {
        this.flightsCount = MIN_FLIGHTS_AMOUNT;
        this.cancelledCount = MIN_CANCELLED_FLIGHTS_AMOUNT;
        this.delayedCount = MIN_DELAYED_FLIGHTS_AMOUNT;

        boolean cancelledStatus = flightDelay.getCancelledStatus();
        if (cancelledStatus) {
            this.maxDelay = NO_DELAY_VALUE;
            this.cancelledCount++;
        } else {
            this.maxDelay = flightDelay.getDelayDuration();
            if (this.maxDelay != NO_DELAY_VALUE) {
                this.delayedCount++;
            }
        }
    }

    public static DelaysStat addDelay(DelaysStat delayStat, FlightDelay flightDelay) {
        float newMaxDelay = delayStat.getMaxDelay();
        int newDelayedCount = delayStat.getDelayedCount();
        int newCancelledCount = delayStat.getCancelledCount();

        if (flightDelay.getCancelledStatus()) {
            newCancelledCount++;
        } else {
            float delayValue = flightDelay.getDelayDuration();
            if (delayValue != NO_DELAY_VALUE) {
                newDelayedCount++;
                if (delayValue > newMaxDelay) {
                    newMaxDelay = delayValue;
                }
            }
        }
        return new DelaysStat(
                newMaxDelay,
                delayStat.getFlightsCount() + 1,
                newDelayedCount,
                newCancelledCount
        );
    }

    public static DelaysStat add(DelaysStat a, DelaysStat b) {
        float maxDelay1 = a.getMaxDelay();
        float maxDelay2 = b.getMaxDelay();
        if ( maxDelay2 > maxDelay1) {
            maxDelay1 = maxDelay2;
        }
        return new DelaysStat(
                maxDelay1,
                a.getFlightsCount() + b.getFlightsCount(),
                a.getDelayedCount() + b.getDelayedCount(),
                a.getCancelledCount() + b.getCancelledCount()
        );
    }

    protected float getMaxDelay() {
        return this.maxDelay;
    }

    protected int getFlightsCount() {
        return this.flightsCount;
    }

    protected int getDelayedCount() {
        return this.delayedCount;
    }

    protected int getCancelledCount() {
        return this.cancelledCount;
    }

    @Override
    public String toString() {
        float percentOfCancelledFlights = cancelledCount / flightsCount * 100; //TODO: constant
        float percentOfDelayedFlights = delayedCount / flightsCount * 100;
        return departureAirportName + " -> " + destinationAirportName +
                "\nMax delay: " + maxDelay +
                "\n" + percentOfCancelledFlights + " flights were cancelled" +
                "\n" + percentOfDelayedFlights + " flights were delayed";
    }
}
