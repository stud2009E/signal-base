package pab.ta.handler.base.lib.signal;

import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import org.ta4j.core.rules.OverIndicatorRule;
import org.ta4j.core.rules.UnderIndicatorRule;
import pab.ta.handler.base.lib.asset.AssetData;

import java.util.LinkedList;
import java.util.List;

import static pab.ta.handler.base.lib.asset.Direction.BUY;
import static pab.ta.handler.base.lib.asset.Direction.SELL;
import static pab.ta.handler.base.lib.indicator.IndicatorType.CCI14;


public class CciSignalProducer extends AbstractSignalProducer {

    public CciSignalProducer() {
        super(CCI14);
    }

    public List<Signal> getSignals(List<AssetData> assetDataList) {
        List<Signal> signals = new LinkedList<>();

        assetDataList.stream()
                .filter(assetData -> assetData.hasIndicator(CCI14))
                .forEach(assetData -> {
                    Indicator<Num> indicator = assetData.getIndicator(CCI14);
                    var index = indicator.getBarSeries().getEndIndex();

                    rules(assetData.getIndicator(CCI14))
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
                        .setDirection(SELL)
                        .setRule(new OverIndicatorRule(indicator, 100))
                        .setName("CCI > 100"),
                new RuleWrapper()
                        .setType(getType())
                        .setDirection(BUY)
                        .setRule(new UnderIndicatorRule(indicator, -100))
                        .setName("CCI < -100"),
                new RuleWrapper()
                        .setType(getType())
                        .setDirection(SELL)
                        .setRule(new CrossedUpIndicatorRule(indicator, 100))
                        .setName("CCI <> 100"),
                new RuleWrapper()
                        .setType(getType())
                        .setDirection(SELL)
                        .setRule(new CrossedDownIndicatorRule(indicator, -100))
                        .setName("CCI >< -100")
        );
    }

}
