package pab.ta.handler.base.lib.signal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.num.Num;
import pab.ta.handler.base.lib.asset.AssetData;

import java.util.List;

public abstract class AbstractSignalProducer {

    public abstract List<Signal> getSignals(List<AssetData> assetDataList);

    protected abstract List<RuleWrapper> rules(Indicator<Num> indicator);

    protected Signal getSignal(RuleWrapper ruleWrapper, AssetData assetData) {
        return new Signal()
                .setName(ruleWrapper.getName())
                .setInterval(assetData.getInterval())
                .setTicker(assetData.getInfo().getTicker())
                .setCreatedAt(assetData.getCreatedAt());
    }

    @NoArgsConstructor
    @Setter
    @Getter
    @Accessors(chain = true)
    public static class RuleWrapper {
        private String name;
        private Rule rule;
    }
}
