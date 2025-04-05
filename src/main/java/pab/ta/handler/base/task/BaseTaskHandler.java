package pab.ta.handler.base.task;

import lombok.RequiredArgsConstructor;
import pab.ta.handler.base.asset.BaseSeriesContainer;
import pab.ta.handler.base.asset.TimeFrame;
import pab.ta.handler.base.asset.provider.AssetInfoProvider;
import pab.ta.handler.base.asset.provider.DataProvider;
import pab.ta.handler.base.signal.SignalProducer;

import java.util.List;

@RequiredArgsConstructor
public class BaseTaskHandler implements TaskHandler {

    private final SignalStore signalStore;
    private final AssetInfoProvider assetInfoProvider;
    private final DataProvider provider;
    private final List<SignalProducer> rules;

    public void process(TimeFrame tf) {
        assetInfoProvider
                .info()
                .stream()
                .map(assetInfo -> new BaseSeriesContainer(tf, assetInfo, provider))
                .forEach(seriesContainer -> {
                    rules.forEach(rule -> {
                        rule.setContainer(seriesContainer);
                        rule.getSignal().ifPresent(signalStore::put);
                    });
                });
    }
}
