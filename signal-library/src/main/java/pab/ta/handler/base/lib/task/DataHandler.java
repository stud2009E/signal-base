package pab.ta.handler.base.lib.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pab.ta.handler.base.lib.asset.AssetData;
import pab.ta.handler.base.lib.indicator.IndicatorFactory;
import pab.ta.handler.base.lib.indicator.IndicatorType;
import pab.ta.handler.base.lib.provider.DataProvider;

@RequiredArgsConstructor
@Slf4j
public class DataHandler implements IDataHandler {

    private final DataStore dataStore;
    private final DataProvider dataProvider;

    @Override
    public void process(AssetData assetData) {
        for (var name : IndicatorType.values()) {
            var series = dataProvider.getSeries(assetData.getInfo(), assetData.getTimeFrame());

            if (series.isEmpty()) {
                continue;
            }

            var indicator = IndicatorFactory.getInstance(name, series);

            if (indicator.isStable()) {
                assetData.putIndicator(name, indicator);
            }
        }

        dataStore.put(assetData);
    }
}
