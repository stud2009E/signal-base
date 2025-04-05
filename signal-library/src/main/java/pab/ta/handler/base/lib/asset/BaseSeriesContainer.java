package pab.ta.handler.base.lib.asset;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ta4j.core.BarSeries;
import pab.ta.handler.base.lib.asset.provider.DataProvider;

@RequiredArgsConstructor
@Getter
public class BaseSeriesContainer implements SeriesContainer {
    private final TimeFrame timeFrame;
    private final AssetInfo assetInfo;
    private final DataProvider dataProvider;
    private BarSeries series;

    @Override
    public BarSeries getSeries() {
        if (series == null) {
            series = dataProvider.getSeries(assetInfo, timeFrame);
        }
        return series;
    }
}
