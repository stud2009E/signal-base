package pab.ta.handler.base.lib.signal;

import lombok.NoArgsConstructor;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import pab.ta.handler.base.lib.asset.AssetData;

import java.util.LinkedList;
import java.util.List;

import static pab.ta.handler.base.lib.indicator.IndicatorType.MACD;

@NoArgsConstructor
public class MacdSignalProducer extends AbstractSignalProducer {

    public List<Signal> getSignals(List<AssetData> assetDataList) {
        List<Signal> signals = new LinkedList<>();

        assetDataList.stream()
                .filter(assetData -> assetData.hasIndicator(MACD))
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

    protected List<RuleWrapper> rules(Indicator<Num> indicator) {
        MACDIndicator macd = (MACDIndicator) indicator;

        return List.of(
                new RuleWrapper()
                        .setRule(new CrossedUpIndicatorRule(macd, 0))
                        .setName("MACD <> 0"),
                new RuleWrapper()
                        .setRule(new CrossedDownIndicatorRule(macd, 0))
                        .setName("MACD >< 0")
        );
    }

}
