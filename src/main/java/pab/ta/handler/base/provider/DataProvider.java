package pab.ta.handler.base.provider;

import org.ta4j.core.BarSeries;
import pab.ta.handler.base.asset.SeriesIdentity;


public interface DataProvider {

    /**
     * get asset data series
     * @param identity full info about asset
     * @return series with candles
     */
    BarSeries getSeries(SeriesIdentity identity);
}
