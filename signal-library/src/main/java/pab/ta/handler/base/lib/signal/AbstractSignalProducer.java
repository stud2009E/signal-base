package pab.ta.handler.base.lib.signal;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.ta4j.core.Rule;
import pab.ta.handler.base.lib.asset.AssetData;
import pab.ta.handler.base.lib.asset.Direction;
import pab.ta.handler.base.lib.indicator.IndicatorType;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public abstract class AbstractSignalProducer {

    private final Set<IndicatorType> types = new HashSet<>();

    public AbstractSignalProducer(IndicatorType... types) {
        this.types.addAll(List.of(types));
    }

    public List<Signal> getSignals(List<AssetData> assetDataList) {
        var checkedData = filterDataForSignal(assetDataList);

        if (checkedData.isEmpty()) {
            return List.of();
        }

        return produceSignals(checkedData);
    }

    public Signal getSignal(RuleWrapper ruleWrapper, AssetData assetData) {
        return new Signal()
                .setName(ruleWrapper.getName())
                .setInterval(assetData.getInterval())
                .setTicker(assetData.getInfo().getTicker())
                .setDirection(ruleWrapper.getDirection())
                .addType(ruleWrapper.getTypes())
                .setCreatedAt(assetData.getCreatedAt());
    }

    protected abstract List<Signal> produceSignals(List<AssetData> assetDataList);

    protected abstract List<AssetData> filterDataForSignal(List<AssetData> assetDataList);

    @Setter
    @Getter
    @Accessors(chain = true)
    @NoArgsConstructor
    public static class RuleWrapper {
        private String name;
        private Rule rule;
        private Direction direction;
        @Setter(AccessLevel.PRIVATE)
        private Set<IndicatorType> types = new HashSet<>();

        public RuleWrapper(String name, Rule rule, Direction direction, IndicatorType... types) {
            this.name = name;
            this.rule = rule;
            this.direction = direction;
            this.types.addAll(List.of(types));
        }

        public RuleWrapper addType(IndicatorType... types) {
            this.types.addAll(List.of(types));

            return this;
        }

        public RuleWrapper addType(Collection<IndicatorType> types) {
            this.types.addAll(types);

            return this;
        }
    }
}
