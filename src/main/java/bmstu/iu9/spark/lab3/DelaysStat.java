package bmstu.iu9.spark.lab3;

import scala.Serializable;
import scala.Tuple2;

import java.util.Map;

public class DelaysStat implements Serializable {
    private static final int    PERCENT_CONVERSION_CONSTANT = 100;
    private static final int    MIN_FLIGHTS_AMOUNT = 1;
    private static final int    MIN_CANCELLED_FLIGHTS_AMOUNT = 0;
    private static final int    MIN_DELAYED_FLIGHTS_AMOUNT = 0;
    private static final float  NO_DELAY_VALUE = 0.0F;
    private static final String FLOAT_STRING_FORMAT = "%.2f";

    private String      departureAirportName;
    private String      destinationAirportName;
    private float       delayedCount;
    private float       cancelledCount;
    private float       maxDelay;
    private final int   flightsCount;

    protected DelaysStat(Tuple2<Tuple2<String, String>, DelaysStat> delaysBtwAirports, Map<String, String> airportName) {
        this.departureAirportName = airportName.get(delaysBtwAirports._1()._1());
        this.destinationAirportName = airportName.get(delaysBtwAirports._1()._2());

        DelaysStat delaysStatSrc = delaysBtwAirports._2();

        this.maxDelay = delaysStatSrc.getMaxDelay();
        this.flightsCount = delaysStatSrc.getFlightsCount();
        this.delayedCount = delaysStatSrc.getDelayedCount();
        this.cancelledCount = delaysStatSrc.getCancelledCount();
    }

    protected DelaysStat(float maxDelay, int flightsCount, float delayedCount, float cancelledCount) {
        this.maxDelay = maxDelay;
        this.flightsCount = flightsCount;
        this.delayedCount = delayedCount;
        this.cancelledCount = cancelledCount;
    }

    private void updateDelaysStat(FlightDelay flightDelay) {
        if (flightDelay.getCancelledStatus()) {
            this.cancelledCount++;
        } else {
            float delayValue = flightDelay.getDelayDuration();
            if (delayValue != NO_DELAY_VALUE) {
                this.delayedCount++;
                this.maxDelay = getMax(delayValue, this.maxDelay);
            }
        }
    }

    protected DelaysStat(FlightDelay flightDelay) {
        this.flightsCount = MIN_FLIGHTS_AMOUNT;
        this.cancelledCount = MIN_CANCELLED_FLIGHTS_AMOUNT;
        this.delayedCount = MIN_DELAYED_FLIGHTS_AMOUNT;
        this.maxDelay = NO_DELAY_VALUE;

        updateDelaysStat(flightDelay);
    }

    protected static DelaysStat addDelay(DelaysStat delayStat, FlightDelay flightDelay) {
        delayStat.updateDelaysStat(flightDelay);
        return new DelaysStat(
                delayStat.getMaxDelay(),
                delayStat.getFlightsCount() + 1,
                delayStat.getDelayedCount(),
                delayStat.getCancelledCount()
        );
    }

    protected static DelaysStat add(DelaysStat a, DelaysStat b) {
        return new DelaysStat(
                getMax(
                        a.getMaxDelay(),
                        b.getMaxDelay()
                ),
                a.getFlightsCount() + b.getFlightsCount(),
                a.getDelayedCount() + b.getDelayedCount(),
                a.getCancelledCount() + b.getCancelledCount()
        );
    }

    private static float getMax(float num1, float num2) {
        return Math.max(num1, num2);
    }

    protected float getMaxDelay() {
        return this.maxDelay;
    }

    protected int getFlightsCount() {
        return this.flightsCount;
    }

    protected float getDelayedCount() {
        return this.delayedCount;
    }

    protected float getCancelledCount() {
        return this.cancelledCount;
    }

    @Override
    public String toString() {
        String percentOfCancelledFlights = percentage(getCancelledCount(), getFlightsCount());
        String percentOfDelayedFlights = percentage(getDelayedCount(), getFlightsCount());
        return departureAirportName + " -> " + destinationAirportName +
                "\nMax delay: " + maxDelay +
                "\n" + percentOfCancelledFlights + "% flights were cancelled" +
                "\n" + percentOfDelayedFlights + "% flights were delayed\n";
    }

    private static String percentage(float numerator, int denominator) {
        return String.format(FLOAT_STRING_FORMAT, numerator / denominator * PERCENT_CONVERSION_CONSTANT);
    }
}
