package pab.ta.handler.base.lib.asset;

import org.ta4j.core.BarSeries;

public interface SeriesContainer {
    BarSeries getSeries();

    AssetInfo getAssetInfo();

    TimeFrame getTimeFrame();
}