package pab.ta.handler.base.lib.signal;

import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.numeric.NumericIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import org.ta4j.core.rules.OverIndicatorRule;
import pab.ta.handler.base.lib.asset.AssetData;

import java.util.LinkedList;
import java.util.List;

import static pab.ta.handler.base.lib.asset.Direction.SELL;
import static pab.ta.handler.base.lib.indicator.IndicatorType.BB_UP;

public class BBUpSignalProducer extends AbstractSignalProducer {

    public BBUpSignalProducer() {
        super(BB_UP);
    }

    @Override
    protected List<AssetData> filterDataForSignal(List<AssetData> assetDataList) {
        return assetDataList.stream().filter(assetData -> assetData.hasIndicator(BB_UP)).toList();
    }

    @Override
    protected List<Signal> produceSignals(List<AssetData> assetDataList) {
        List<Signal> signals = new LinkedList<>();

        assetDataList.forEach(assetData -> {
            Indicator<Num> indicator = assetData.getIndicator(BB_UP);
            var series = indicator.getBarSeries();

            rules(indicator, NumericIndicator.closePrice(series)).stream().filter(ruleWrapper -> ruleWrapper.getRule().isSatisfied(series.getEndIndex())).forEach(ruleWrapper -> signals.add(getSignal(ruleWrapper, assetData)));
        });

        return signals;
    }

    protected List<RuleWrapper> rules(Indicator<Num> indicator, NumericIndicator closePrice) {
        return List.of(new RuleWrapper().setDirection(SELL).setRule(new OverIndicatorRule(closePrice, indicator)).setName("BB_UP < price").addType(BB_UP), new RuleWrapper().setDirection(SELL).setRule(new CrossedUpIndicatorRule(closePrice, indicator)).setName("BB_UP >< price").addType(BB_UP), new RuleWrapper().setDirection(SELL).setRule(new CrossedDownIndicatorRule(closePrice, indicator)).setName("BB_UP <> price").addType(BB_UP));
    }
}
