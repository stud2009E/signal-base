package pab.ta.handler.base.lib.signal;

import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.numeric.NumericIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.UnderIndicatorRule;
import pab.ta.handler.base.lib.asset.AssetData;

import java.util.LinkedList;
import java.util.List;

import static pab.ta.handler.base.lib.asset.Direction.BUY;
import static pab.ta.handler.base.lib.indicator.IndicatorType.BB_LOW;

public class BBLowSignalProducer extends AbstractSignalProducer {

    public BBLowSignalProducer() {
        super(BB_LOW);
    }

    @Override
    protected List<AssetData> filterDataForSignal(List<AssetData> assetDataList) {
        return assetDataList.stream()
                .filter(assetData -> assetData.hasIndicator(BB_LOW))
                .toList();
    }

    @Override
    protected List<Signal> produceSignals(List<AssetData> assetDataList) {
        List<Signal> signals = new LinkedList<>();

        assetDataList
                .forEach(assetData -> {
                    Indicator<Num> indicator = assetData.getIndicator(BB_LOW);
                    var series = indicator.getBarSeries();

                    rules(indicator, NumericIndicator.closePrice(series))
                            .stream()
                            .filter(ruleWrapper -> ruleWrapper.getRule().isSatisfied(series.getEndIndex()))
                            .forEach(ruleWrapper -> signals.add(getSignal(ruleWrapper, assetData)));
                });

        return signals;
    }

    protected List<RuleWrapper> rules(Indicator<Num> indicator, NumericIndicator closePrice) {
        return List.of(
                new RuleWrapper()
                        .setDirection(BUY)
                        .setRule(new UnderIndicatorRule(closePrice, indicator))
                        .setName("BB_LOW > price")
                        .addType(BB_LOW),
                new RuleWrapper()
                        .setDirection(BUY)
                        .setRule(new CrossedDownIndicatorRule(closePrice, indicator))
                        .setName("BB_LOW <> price")
                        .addType(BB_LOW)
        );
    }
}
