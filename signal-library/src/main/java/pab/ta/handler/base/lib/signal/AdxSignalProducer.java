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

    public AdxSignalProducer(SignalProcessor processor) {
        super(processor, ADX14);
    }

    @Override
    public void process(List<AssetData> assetDataList) {
        List<Signal> signals = new LinkedList<>();

        assetDataList.stream()
                .filter(assetData -> assetData.hasIndicator(ADX14))
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

        if (!signals.isEmpty()) {
            getSignalProcessor().process(assetDataList.getFirst().getInfo(), signals);
        }
    }

    protected List<RuleWrapper> rules(Indicator<Num> adx, Indicator<Num> adxPlus, Indicator<Num> adxMinus) {
        return List.of(
                new RuleWrapper()
                        .addType(getIndicatorTypes())
                        .setDirection(BUY)
                        .setRule(
                                new OverIndicatorRule(adx, 20)
                                        .and(new UnderIndicatorRule(adx, 25))
                                        .and(new CrossedUpIndicatorRule(adxPlus, adxMinus)))
                        .setName("ADX > 20 | D+ <> D-"),

                new RuleWrapper()
                        .addType(getIndicatorTypes())
                        .setDirection(SELL)
                        .setRule(
                                new OverIndicatorRule(adx, 20)
                                        .and(new UnderIndicatorRule(adx, 25))
                                        .and(new CrossedUpIndicatorRule(adxMinus, adxPlus)))
                        .setName("ADX > 20 | D+ >< D-"),

                new RuleWrapper()
                        .addType(getIndicatorTypes())
                        .setDirection(BUY)
                        .setRule(
                                new OverIndicatorRule(adx, 25)
                                        .and(new OverIndicatorRule(adxPlus, adxMinus)))
                        .setName("ADX > 25 | D+ > D-"),
                new RuleWrapper()
                        .addType(getIndicatorTypes())
                        .setDirection(SELL)
                        .setRule(
                                new OverIndicatorRule(adx, 25)
                                        .and(new OverIndicatorRule(adxMinus, adxPlus)))
                        .setName("ADX > 25 | D+ < D-")
        );
    }

}
