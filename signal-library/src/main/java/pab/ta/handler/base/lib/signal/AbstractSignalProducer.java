package pab.ta.handler.base.lib.signal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.num.Num;
import pab.ta.handler.base.lib.asset.AssetData;
import pab.ta.handler.base.lib.asset.Direction;
import pab.ta.handler.base.lib.indicator.IndicatorType;

import java.util.List;

@Getter
@RequiredArgsConstructor
public abstract class AbstractSignalProducer {

    private final IndicatorType type;

    public abstract List<Signal> getSignals(List<AssetData> assetDataList);

    protected abstract List<RuleWrapper> rules(Indicator<Num> indicator);

    public Signal getSignal(RuleWrapper ruleWrapper, AssetData assetData) {
        return new Signal()
                .setName(ruleWrapper.getName())
                .setInterval(assetData.getInterval())
                .setTicker(assetData.getInfo().getTicker())
                .setDirection(ruleWrapper.getDirection())
                .setType(ruleWrapper.getType())
                .setCreatedAt(assetData.getCreatedAt());
    }

    @NoArgsConstructor
    @Setter
    @Getter
    @Accessors(chain = true)
    public static class RuleWrapper {
        private String name;
        private IndicatorType type;
        private Rule rule;
        private Direction direction;
    }
}
