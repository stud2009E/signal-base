package pab.ta.handler.base.lib.task;

import lombok.RequiredArgsConstructor;
import pab.ta.handler.base.lib.asset.AssetData;
import pab.ta.handler.base.lib.asset.ITimeFrame;
import pab.ta.handler.base.lib.asset.SeriesContainer;
import pab.ta.handler.base.lib.indicator.IndicatorFactory;
import pab.ta.handler.base.lib.indicator.SupportedIndicator;
import pab.ta.handler.base.lib.provider.AssetInfoProvider;
import pab.ta.handler.base.lib.provider.DataProvider;
import pab.ta.handler.base.lib.util.Utils;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
public class TaskHandler implements ITaskHandler {

    private final IStore<AssetData> dataStore;
    private final AssetInfoProvider assetInfoProvider;
    private final DataProvider provider;

    public void process(ITimeFrame tf) {

        assetInfoProvider
                .info()
                .stream()
                .map(assetInfo -> new SeriesContainer(tf, assetInfo, provider))
                .forEach(seriesContainer -> {

                    var assetData = AssetData.builder()
                            .info(seriesContainer.getAssetInfo())
                            .interval(tf.getInterval())
                            .createdAt(ZonedDateTime.now())
                            .build();

                    for (var name : SupportedIndicator.values()) {
                        var indicator = IndicatorFactory.getInstance(name, seriesContainer.getSeries());

                        var data = Utils.getLast5Values(indicator);
                        if (!data.isEmpty()) {
                            assetData.putValue(name, data);
                        }
                    }

                    dataStore.put(assetData);
                });
    }
}
