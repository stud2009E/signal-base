package pab.ta.handler.base.lib.signal;

import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.*;
import org.ta4j.core.rules.helper.ChainLink;
import pab.ta.handler.base.lib.asset.AssetData;

import java.util.LinkedList;
import java.util.List;

import static pab.ta.handler.base.lib.asset.Direction.BUY;
import static pab.ta.handler.base.lib.asset.Direction.SELL;
import static pab.ta.handler.base.lib.indicator.IndicatorType.RSI14;

public class RsiSignalProducer extends AbstractSignalProducer {

    public RsiSignalProducer() {
        super(RSI14);
    }

    @Override
    protected List<Signal> produceSignals(List<AssetData> assetDataList) {
        List<Signal> signals = new LinkedList<>();

        assetDataList
                .forEach(assetData -> {
                    Indicator<Num> indicator = assetData.getIndicator(RSI14);
                    var index = indicator.getBarSeries().getEndIndex();

                    rules(indicator)
                            .stream()
                            .filter(ruleWrapper -> ruleWrapper.getRule().isSatisfied(index))
                            .forEach(ruleWrapper -> signals.add(getSignal(ruleWrapper, assetData)));
                });

        return signals;
    }

    @Override
    protected List<AssetData> filterDataForSignal(List<AssetData> assetDataList) {
        return assetDataList.stream()
                .filter(assetData -> assetData.hasIndicator(RSI14))
                .toList();
    }

    protected List<RuleWrapper> rules(Indicator<Num> indicator) {

        var over = new OverIndicatorRule(indicator, 70);
        var under = new UnderIndicatorRule(indicator, 30);
        var crossUp30 = new CrossedUpIndicatorRule(indicator, 30);
        var crossUp70 = new CrossedUpIndicatorRule(indicator, 70);
        var crossDown70 = new CrossedDownIndicatorRule(indicator, 70);
        var crossDown30 = new CrossedDownIndicatorRule(indicator, 70);

        var waveDown = new ChainRule(crossDown30, new ChainLink(crossUp30, 10), new ChainLink(crossDown30, 10));
        var waveUp = new ChainRule(crossUp70, new ChainLink(crossDown70, 10), new ChainLink(crossUp70, 10));

        return List.of(
                new RuleWrapper()
                        .addType(RSI14)
                        .setDirection(SELL)
                        .setRule(over)
                        .setName("RSI > 70"),
                new RuleWrapper()
                        .addType(RSI14)
                        .setDirection(BUY)
                        .setRule(under)
                        .setName("RSI < 30"),
                new RuleWrapper()
                        .addType(RSI14)
                        .setDirection(SELL)
                        .setRule(waveDown)
                        .setName("2x RSI >< 30"),
                new RuleWrapper()
                        .addType(RSI14)
                        .setDirection(BUY)
                        .setRule(waveUp)
                        .setName("2x RSI <> 70")
        );
    }

}
