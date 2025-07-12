package pab.ta.handler.base.lib.asset;

import org.ta4j.core.BarSeries;

/**
 * Container for financial time series data with associated metadata.
 */
public interface ISeriesContainer {

    /**
     * @return The complete BarSeries containing price/volume data
     */
    BarSeries getSeries();

    /**
     * @return Information about the financial instrument (asset)
     */
    IAssetInfo getAssetInfo();

    /**
     * @return The time frame (resolution) of the series data (e.g. 1m, 15m, 1h, etc.)
     */
    ITimeFrame getTimeFrame();
}