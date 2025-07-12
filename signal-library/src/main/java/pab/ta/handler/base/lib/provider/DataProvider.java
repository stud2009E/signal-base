package pab.ta.handler.base.lib.provider;

import org.ta4j.core.BarSeries;
import pab.ta.handler.base.lib.asset.IAssetInfo;
import pab.ta.handler.base.lib.asset.ITimeFrame;


/**
 * Provides access to historical market data for specified assets and time ranges.
 */
public interface DataProvider {

    /**
     * Retrieves a time series of market data (candles/bars) for the given asset and time frame.
     *
     * @param info The asset to retrieve data for
     * @param tf   The time frame specifying both interval and date range
     * @return BarSeries containing the historical price data
     */
    BarSeries getSeries(IAssetInfo info, ITimeFrame tf);
}