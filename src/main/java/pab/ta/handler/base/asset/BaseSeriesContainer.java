package pab.ta.handler.base.asset;

import org.ta4j.core.BarSeries;
import pab.ta.handler.base.provider.DataProvider;

public class BaseSeriesContainer implements SeriesContainer {
    private final SeriesIdentity seriesIdentity;
    private final DataProvider dataProvider;
    private BarSeries series;

    public BaseSeriesContainer(DataProvider dataProvider, SeriesIdentity seriesIdentity) {
        this.seriesIdentity = seriesIdentity;
        this.dataProvider = dataProvider;
    }

    @Override
    public BarSeries series() {
        if (series == null) {
            series = dataProvider.getSeries(seriesIdentity);
        }
        return series;
    }

    @Override
    public SeriesIdentity identity() {
        return seriesIdentity;
    }
}
