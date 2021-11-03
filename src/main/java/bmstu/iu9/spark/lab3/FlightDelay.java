package bmstu.iu9.spark.lab3;

import scala.Serializable;

public class FlightDelay implements Serializable {
    private static final int    CANCELLED_STATUS_INDEX = 20;
    private static final int    DELAY_DURATION_INDEX = 17;
    private static final float  DEFAULT_DELAY_VALUE = 0.0F;

    private final boolean cancelledStatus;
    private final float   delayDuration;

    public FlightDelay(String[] flightData) {
        String cancelledStatus = flightData[CANCELLED_STATUS_INDEX];
        if (cancelledStatus.isEmpty()) {
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

    @Override
    public String toString() {
        return "cancelledStatus=" + cancelledStatus +
                ", delayDuration=" + delayDuration;
    }
}
