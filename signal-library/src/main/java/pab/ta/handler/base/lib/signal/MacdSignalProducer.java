package pab.ta.handler.base.lib.signal;

import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import pab.ta.handler.base.lib.asset.AssetData;

import java.util.LinkedList;
import java.util.List;

import static pab.ta.handler.base.lib.asset.Direction.BUY;
import static pab.ta.handler.base.lib.asset.Direction.SELL;
import static pab.ta.handler.base.lib.indicator.IndicatorType.MACD;


public class MacdSignalProducer extends AbstractSignalProducer {

    public MacdSignalProducer() {
        super(MACD);
    }

    @Override
    protected List<Signal> produceSignals(List<AssetData> assetDataList) {
        List<Signal> signals = new LinkedList<>();

        assetDataList
                .forEach(assetData -> {
                    Indicator<Num> indicator = assetData.getIndicator(MACD);
                    var index = indicator.getBarSeries().getEndIndex();

                    rules(assetData.getIndicator(MACD))
                            .stream()
                            .filter(ruleWrapper -> ruleWrapper.getRule().isSatisfied(index))
                            .forEach(ruleWrapper -> signals.add(getSignal(ruleWrapper, assetData)));
                });

        return signals;
    }

    @Override
    protected List<AssetData> filterDataForSignal(List<AssetData> assetDataList) {
        return assetDataList.stream()
                .filter(assetData -> assetData.hasIndicator(MACD))
                .toList();
    }

    protected List<RuleWrapper> rules(Indicator<Num> indicator) {
        MACDIndicator macd = (MACDIndicator) indicator;

        return List.of(
                new RuleWrapper()
                        .addType(MACD)
                        .setDirection(BUY)
                        .setRule(new CrossedUpIndicatorRule(macd, 0))
                        .setName("MACD <> 0"),
                new RuleWrapper()
                        .addType(MACD)
                        .setDirection(SELL)
                        .setRule(new CrossedDownIndicatorRule(macd, 0))
                        .setName("MACD >< 0")
        );
    }

}
