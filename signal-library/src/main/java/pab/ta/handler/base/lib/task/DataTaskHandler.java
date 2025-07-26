package pab.ta.handler.base.lib.task;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import pab.ta.handler.base.lib.asset.AssetData;
import pab.ta.handler.base.lib.asset.TimeFrame;
import pab.ta.handler.base.lib.indicator.IndicatorFactory;
import pab.ta.handler.base.lib.indicator.IndicatorType;
import pab.ta.handler.base.lib.provider.AssetInfoProvider;
import pab.ta.handler.base.lib.provider.DataProvider;

import java.util.Objects;

@RequiredArgsConstructor
@Setter
@Slf4j
public class DataTaskHandler implements IDataTaskHandler {

    private final DataStore dataStore;
    private final AssetInfoProvider infoProvider;
    private final DataProvider dataProvider;
    private TimeFrame timeFrame;

    public void process() {
        if (Objects.isNull(timeFrame)) {
            log.info("Task handler: time frame is empty");

            return;
        }
        log.info("Task start: period {}", timeFrame.getInterval().name());

        infoProvider
                .info()
                .forEach(assetInfo -> {
                    var assetData = AssetData.builder()
                            .info(assetInfo)
                            .interval(timeFrame.getInterval())
                            .createdAt(timeFrame.getTo())
                            .build();

                    for (var name : IndicatorType.values()) {
                        var series = dataProvider.getSeries(assetInfo, timeFrame);

                        if (series.isEmpty()) {
                            continue;
                        }

                        var indicator = IndicatorFactory.getInstance(name, series);

                        if (indicator.isStable()) {
                            assetData.putIndicator(name, indicator);
                        }
                    }

                    dataStore.put(assetData);
                });
    }
}
