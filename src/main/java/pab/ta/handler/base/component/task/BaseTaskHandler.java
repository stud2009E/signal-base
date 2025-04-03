package pab.ta.handler.base.component.task;

import com.google.common.eventbus.EventBus;
import lombok.RequiredArgsConstructor;
import pab.ta.handler.base.asset.BaseSeriesContainer;
import pab.ta.handler.base.asset.TimeFrame;
import pab.ta.handler.base.component.rule.SignalRule;
import pab.ta.handler.base.provider.AssetInfoProvider;
import pab.ta.handler.base.provider.DataProvider;
import pab.ta.handler.base.task.SignalStore;
import pab.ta.handler.base.task.TaskHandler;

import java.util.List;

@RequiredArgsConstructor
public class BaseTaskHandler extends EventBus implements TaskHandler {

    private final SignalStore signalStore;
    private final List<SignalRule> rules;
    private final List<AssetInfoProvider> infoProviders;
    private final DataProvider provider;


    public void process(TimeFrame tf) {
        infoProviders.forEach(infoProvider -> {
            infoProvider
                    .info()
                    .stream()
                    .map(assetInfo -> new BaseSeriesContainer(tf, assetInfo, provider))
                    .forEach(seriesContainer -> {
                        rules.forEach(rule -> {
                            rule.setContainer(seriesContainer);
                            rule.getSignal().ifPresent(signalStore::put);
                        });
                    });
        });
    }
}
