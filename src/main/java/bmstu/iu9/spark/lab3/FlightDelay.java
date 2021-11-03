package bmstu.iu9.spark.lab3;

import scala.Serializable;

public class FlightDelay implements Serializable {
    private static final int    CANCELLED_STATUS_INDEX = 0;
    private static final int    DELAY_DURATION_INDEX = 0;
    private static final float  DEFAULT_DELAY_VALUE = 0;

    private boolean cancelledStatus;
    private float   delayDuration;

    public FlightDelay(String[] flightData) {
        String cancelledStatus = flightData[CANCELLED_STATUS_INDEX];
        if (cancelledStatus.isEmpty()) {
            this.cancelledStatus = false;
            this.delayDuration = 
        } else {
            this.cancelledStatus = true;
            this.delayDuration = DEFAULT_DELAY_VALUE;
        }
        this.cancelledStatus = cancelledStatus;
        this.delayDuration = delayDuration;
    }
}
