package pab.ta.handler.base.lib.signal;

import lombok.RequiredArgsConstructor;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.adx.ADXIndicator;
import org.ta4j.core.indicators.adx.MinusDIIndicator;
import org.ta4j.core.indicators.adx.PlusDIIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import org.ta4j.core.rules.OverIndicatorRule;
import org.ta4j.core.rules.UnderIndicatorRule;
import pab.ta.handler.base.lib.asset.AssetData;
import pab.ta.handler.base.lib.asset.CandleInterval;
import pab.ta.handler.base.lib.task.AssetDataProcessor;

import java.util.LinkedList;
import java.util.List;

import static pab.ta.handler.base.lib.asset.Direction.BUY;
import static pab.ta.handler.base.lib.asset.Direction.SELL;

@RequiredArgsConstructor
public class AdxSignalProducer implements AssetDataProcessor {

    private final SignalProcessor signalProcessor;

    @Override
    public void process(List<AssetData> assetDataList) {
        List<Signal> signalList = new LinkedList<>();

        assetDataList.forEach(assetData -> {

            var series = assetData.getBarSeries();

            var adx = new ADXIndicator(series, 14);
            var adxPlus = new PlusDIIndicator(series, 14);
            var adxMinus = new MinusDIIndicator(series, 14);
            var index = adx.getBarSeries().getEndIndex();

            signals(assetData.getTicker(), assetData.getInterval(), adx, adxPlus, adxMinus)
                    .stream()
                    .filter(signal -> signal.getRule().isSatisfied(index))
                    .forEach(signalList::add);
        });

        if (!signalList.isEmpty()) {
            signalProcessor.process(assetDataList.getFirst().getInfo(), signalList);
        }
    }

    protected List<Signal> signals(String ticker, CandleInterval interval,
                                   Indicator<Num> adx, Indicator<Num> adxPlus, Indicator<Num> adxMinus) {
        return List.of(
                Signal.builder()
                        .name("ADX > 20 | D+ <> D-")
                        .ticker(ticker)
                        .interval(interval)
                        .direction(BUY)
                        .rule(new OverIndicatorRule(adx, 20)
                                .and(new UnderIndicatorRule(adx, 25))
                                .and(new CrossedUpIndicatorRule(adxPlus, adxMinus)))
                        .build(),

                Signal.builder()
                        .name("ADX > 20 | D+ >< D-")
                        .ticker(ticker)
                        .interval(interval)
                        .direction(SELL)
                        .rule(new OverIndicatorRule(adx, 20)
                                .and(new UnderIndicatorRule(adx, 25))
                                .and(new CrossedUpIndicatorRule(adxMinus, adxPlus)))
                        .build(),

                Signal.builder()
                        .name("ADX > 25 | D+ > D-")
                        .ticker(ticker)
                        .interval(interval)
                        .direction(BUY)
                        .rule(new OverIndicatorRule(adx, 25)
                                .and(new OverIndicatorRule(adxPlus, adxMinus)))
                        .build(),

                Signal.builder()
                        .name("ADX > 25 | D+ < D-")
                        .ticker(ticker)
                        .interval(interval)
                        .direction(SELL)
                        .rule(new OverIndicatorRule(adx, 25)
                                .and(new OverIndicatorRule(adxMinus, adxPlus)))
                        .build()
        );
    }

}
