package pab.ta.handler.base.component.task;

import com.google.common.eventbus.EventBus;
import org.springframework.stereotype.Component;
import org.ta4j.core.BarSeries;
import pab.ta.handler.base.asset.AssetType;
import pab.ta.handler.base.asset.BaseSeriesContainer;
import pab.ta.handler.base.asset.BaseSeriesIdentity;
import pab.ta.handler.base.asset.TimeFrame;
import pab.ta.handler.base.component.rule.RuleWrapper;
import pab.ta.handler.base.provider.AssetInfoProvider;
import pab.ta.handler.base.provider.DataProvider;
import pab.ta.handler.base.task.SignalStore;
import pab.ta.handler.base.task.TaskHandler;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class BaseTaskHandler extends EventBus implements TaskHandler {

    private final SignalStore signalStore;
    private final List<RuleWrapper> ruleWrappers;
    private final List<AssetInfoProvider> infoProviders;
    private final DataProvider provider;

    public BaseTaskHandler(SignalStore signalStore, List<RuleWrapper> ruleWrappers, List<AssetInfoProvider> infoProviders, DataProvider provider) {
        super();
        this.signalStore = signalStore;
        this.ruleWrappers = ruleWrappers;
        this.infoProviders = infoProviders;
        this.provider = provider;
    }

    public void process(TimeFrame tf) {
        AtomicBoolean isStoreUpdated = new AtomicBoolean(false);

        infoProviders.forEach(infoProvider -> {
            infoProvider
                    .info()
                    .stream()
                    .map(assetInfo -> new BaseSeriesIdentity(assetInfo, tf))
                    .map(identity -> new BaseSeriesContainer(provider, identity))
                    .forEach(seriesContainer -> {
                        BarSeries series = seriesContainer.series();
                        AssetType type = seriesContainer.identity().info().type();

                        if (series.isEmpty()) {
                            return;
                        }

                        ruleWrappers.forEach(ruleWrapper -> {
                            if (ruleWrapper.applicableForType(type)
                                    && ruleWrapper.applicableForInterval(tf.interval())) {
                                ruleWrapper.setSeriesContainer(seriesContainer);

                                ruleWrapper.rules().forEach(ruleIdentity -> {
                                    if (ruleIdentity.rule().isSatisfied(series.getEndIndex())) {
                                        boolean isAdded = signalStore.put(ruleIdentity);

                                        if (isAdded && !isStoreUpdated.get()){
                                            isStoreUpdated.set(true);
                                        }
                                    }
                                });
                            }
                        });
                    });
        });

        if (isStoreUpdated.get()) {
            post(tf);
        }
    }
}
