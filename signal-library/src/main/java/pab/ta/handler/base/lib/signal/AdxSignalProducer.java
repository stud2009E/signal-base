package pab.ta.handler.base.lib.signal;

import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import org.ta4j.core.rules.OverIndicatorRule;
import org.ta4j.core.rules.UnderIndicatorRule;
import pab.ta.handler.base.lib.asset.AssetData;

import java.util.LinkedList;
import java.util.List;

import static pab.ta.handler.base.lib.asset.Direction.BUY;
import static pab.ta.handler.base.lib.asset.Direction.SELL;
import static pab.ta.handler.base.lib.indicator.IndicatorType.*;


public class AdxSignalProducer extends AbstractSignalProducer {

    public AdxSignalProducer() {
        super(ADX14);
    }

    @Override
    protected List<Signal> produceSignals(List<AssetData> assetDataList) {
        List<Signal> signals = new LinkedList<>();

        assetDataList
                .forEach(assetData -> {
                    Indicator<Num> adx = assetData.getIndicator(ADX14);
                    Indicator<Num> adxPlus = assetData.getIndicator(ADX_PLUS14);
                    Indicator<Num> adxMinus = assetData.getIndicator(ADX_MINUS14);
                    var index = adx.getBarSeries().getEndIndex();

                    rules(adx, adxPlus, adxMinus)
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

    protected List<RuleWrapper> rules(Indicator<Num> adx, Indicator<Num> adxPlus, Indicator<Num> adxMinus) {
        return List.of(
                new RuleWrapper()
                        .addType(getTypes())
                        .setDirection(BUY)
                        .setRule(
                                new OverIndicatorRule(adx, 20)
                                        .and(new UnderIndicatorRule(adx, 25))
                                        .and(new CrossedUpIndicatorRule(adxPlus, adxMinus)))
                        .setName("ADX > 20 | D+ <> D-"),

                new RuleWrapper()
                        .addType(getTypes())
                        .setDirection(SELL)
                        .setRule(
                                new OverIndicatorRule(adx, 20)
                                        .and(new UnderIndicatorRule(adx, 25))
                                        .and(new CrossedUpIndicatorRule(adxMinus, adxPlus)))
                        .setName("ADX > 20 | D+ >< D-"),

                new RuleWrapper()
                        .addType(getTypes())
                        .setDirection(BUY)
                        .setRule(
                                new OverIndicatorRule(adx, 25)
                                        .and(new OverIndicatorRule(adxPlus, adxMinus)))
                        .setName("ADX > 25 | D+ > D-"),
                new RuleWrapper()
                        .addType(getTypes())
                        .setDirection(SELL)
                        .setRule(
                                new OverIndicatorRule(adx, 25)
                                        .and(new OverIndicatorRule(adxMinus, adxPlus)))
                        .setName("ADX > 25 | D+ < D-")
        );
    }

}
