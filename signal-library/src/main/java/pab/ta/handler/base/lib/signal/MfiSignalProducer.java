package pab.ta.handler.base.lib.signal;

import lombok.NoArgsConstructor;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import org.ta4j.core.rules.OverIndicatorRule;
import org.ta4j.core.rules.UnderIndicatorRule;
import pab.ta.handler.base.lib.asset.AssetData;

import java.util.LinkedList;
import java.util.List;

import static pab.ta.handler.base.lib.indicator.IndicatorType.MFI14;

@NoArgsConstructor
public class MfiSignalProducer extends AbstractSignalProducer {

    public List<Signal> getSignals(List<AssetData> assetDataList) {
        List<Signal> signals = new LinkedList<>();

        assetDataList.stream()
                .filter(assetData -> assetData.hasIndicator(MFI14))
                .forEach(assetData -> {
                    Indicator<Num> indicator = assetData.getIndicator(MFI14);
                    var index = indicator.getBarSeries().getEndIndex();

                    rules(assetData.getIndicator(MFI14))
                            .stream()
                            .filter(ruleWrapper -> ruleWrapper.getRule().isSatisfied(index))
                            .forEach(ruleWrapper -> signals.add(getSignal(ruleWrapper, assetData)));
                });

        return signals;
    }

    protected List<RuleWrapper> rules(Indicator<Num> indicator) {
        return List.of(
                new RuleWrapper()
                        .setRule(new OverIndicatorRule(indicator, 80))
                        .setName("MFI > 80"),
                new RuleWrapper()
                        .setRule(new UnderIndicatorRule(indicator, 20))
                        .setName("MFI < 20"),
                new RuleWrapper()
                        .setRule(new CrossedUpIndicatorRule(indicator, 20))
                        .setName("MFI <> 20"),
                new RuleWrapper()
                        .setRule(new CrossedDownIndicatorRule(indicator, 80))
                        .setName("MFI >< 80")
        );
    }

}
