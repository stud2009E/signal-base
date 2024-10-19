package pab.ta.handler.base.asset;

import org.ta4j.core.BarSeries;

public interface SeriesContainer {
    BarSeries series();

    SeriesIdentity identity();
}