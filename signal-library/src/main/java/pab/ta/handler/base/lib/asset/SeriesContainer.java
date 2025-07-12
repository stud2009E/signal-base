package pab.ta.handler.base.lib.asset;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ta4j.core.BarSeries;
import pab.ta.handler.base.lib.provider.DataProvider;

@RequiredArgsConstructor
@Getter
public class SeriesContainer implements ISeriesContainer {
    private final ITimeFrame timeFrame;
    private final IAssetInfo assetInfo;
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
