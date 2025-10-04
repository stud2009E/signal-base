package pab.ta.handler.base.lib.task;

import org.ta4j.core.BarSeries;
import pab.ta.handler.base.lib.asset.AssetInfo;
import pab.ta.handler.base.lib.asset.TimeFrame;

public interface SeriesProcessor {

    void beforeProcess(AssetInfo assetInfo);

    void process(BarSeries series, TimeFrame timeFrame);

    void afterProcess();
}
