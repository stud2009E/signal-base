package pab.ta.handler.base.lib.signal;

import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.OverIndicatorRule;
import pab.ta.handler.base.lib.asset.AssetData;

import java.util.LinkedList;
import java.util.List;

import static pab.ta.handler.base.lib.asset.Direction.HOLD;
import static pab.ta.handler.base.lib.indicator.IndicatorType.ADX14;


public class AdxSignalProducer extends AbstractSignalProducer {

    public AdxSignalProducer() {
        super(ADX14);
    }

    public List<Signal> getSignals(List<AssetData> assetDataList) {
        List<Signal> signals = new LinkedList<>();

        assetDataList.stream()
                .filter(assetData -> assetData.hasIndicator(ADX14))
                .forEach(assetData -> {
                    Indicator<Num> indicator = assetData.getIndicator(ADX14);
                    var index = indicator.getBarSeries().getEndIndex();

                    rules(assetData.getIndicator(ADX14))
                            .stream()
                            .filter(ruleWrapper -> ruleWrapper.getRule().isSatisfied(index))
                            .forEach(ruleWrapper -> signals.add(getSignal(ruleWrapper, assetData)));
                });

        return signals;
    }

    protected List<RuleWrapper> rules(Indicator<Num> indicator) {
        return List.of(
                new RuleWrapper()
                        .setType(getType())
                        .setDirection(HOLD)
                        .setRule(new OverIndicatorRule(indicator, 25))
                        .setName("ADX > 25 ()")
        );
    }

}
