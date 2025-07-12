package pab.ta.handler.base.lib.asset;

import java.time.ZonedDateTime;

/**
 * Represents a time range with a specific candle interval for financial data.
 */
public interface ITimeFrame {

    /**
     * @return The candle interval of the data
     */
    CandleInterval getInterval();

    /**
     * @return The starting timestamp of the time range
     */
    ZonedDateTime getFrom();

    /**
     * @return The ending timestamp  of the time range
     */
    ZonedDateTime getTo();
}
