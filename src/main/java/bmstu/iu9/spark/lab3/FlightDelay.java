package bmstu.iu9.spark.lab3;

import scala.Serializable;
import scala.Tuple2;

public class FlightDelay implements Serializable {
    private static final int    CANCELLED_STATUS_INDEX = 21;
    private static final int    DELAY_DURATION_INDEX = 17;
    private static final float  DEFAULT_DELAY_VALUE = 0.0F;
    private static final int    DEPARTURE_AIRPORT_ID_INDEX = 11;
    private static final int    DESTINATION_AIRPORT_ID_INDEX = 14;

    private final boolean cancelledStatus;
    private final float   delayDuration;
    private final String  departureAirportID;
    private final String  destinationAirportID;

    public FlightDelay(String[] flightData) {
        this.departureAirportID = flightData[DEPARTURE_AIRPORT_ID_INDEX];
        this.destinationAirportID = flightData[DESTINATION_AIRPORT_ID_INDEX];

        String cancelledStatus = flightData[CANCELLED_STATUS_INDEX];

        if (!cancelledStatus.isEmpty()) {
            this.cancelledStatus = false;
            this.delayDuration = Float.parseFloat(
                    flightData[DELAY_DURATION_INDEX]
            );
        } else {
            this.cancelledStatus = true;
            this.delayDuration = DEFAULT_DELAY_VALUE;
        }
    }

    protected boolean getCancelledStatus() {
        return this.cancelledStatus;
    }

    protected float getDelayDuration() {
        return this.delayDuration;
    }

    protected Tuple2<String, String> makePairOfDepartureAndDestinationAirportIDs() {
        return new Tuple2<>(
                this.departureAirportID,
                this.destinationAirportID
        );
    }

    @Override
    public String toString() {
        return "cancelledStatus=" + cancelledStatus +
                ", delayDuration=" + delayDuration;
    }
}
