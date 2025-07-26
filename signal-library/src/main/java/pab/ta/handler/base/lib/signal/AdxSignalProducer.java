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

    @Override
    protected List<Signal> produceSignals(List<AssetData> assetDataList) {
        List<Signal> signals = new LinkedList<>();

        assetDataList
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

    @Override
    protected List<AssetData> filterDataForSignal(List<AssetData> assetDataList) {
        return assetDataList.stream()
                .filter(assetData -> assetData.hasIndicator(ADX14))
                .toList();
    }

    protected List<RuleWrapper> rules(Indicator<Num> indicator) {
        return List.of(
                new RuleWrapper()
                        .addType(getTypes())
                        .setDirection(HOLD)
                        .setRule(new OverIndicatorRule(indicator, 25))
                        .setName("ADX > 25")
        );
    }

}
