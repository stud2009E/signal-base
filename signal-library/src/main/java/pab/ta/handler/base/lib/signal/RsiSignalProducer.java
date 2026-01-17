package pab.ta.handler.base.lib.signal;

import lombok.RequiredArgsConstructor;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.*;
import org.ta4j.core.rules.helper.ChainLink;
import pab.ta.handler.base.lib.asset.AssetData;
import pab.ta.handler.base.lib.asset.CandleInterval;
import pab.ta.handler.base.lib.task.AssetDataProcessor;

import java.util.LinkedList;
import java.util.List;

import static pab.ta.handler.base.lib.asset.Direction.BUY;
import static pab.ta.handler.base.lib.asset.Direction.SELL;

@RequiredArgsConstructor
public class RsiSignalProducer implements AssetDataProcessor {

    private final SignalProcessor signalProcessor;

    @Override
    public void process(List<AssetData> assetDataList) {
        List<Signal> signalList = new LinkedList<>();

        assetDataList.forEach(assetData -> {
            var series = assetData.getBarSeries();

            var closePrice = new ClosePriceIndicator(series);
            var indicator = new RSIIndicator(closePrice, 14);
            var index = indicator.getBarSeries().getEndIndex();

            signals(assetData.getTicker(), assetData.getInterval(), indicator)
                    .stream()
                    .filter(signal -> signal.getRule().isSatisfied(index))
                    .forEach(signalList::add);
        });

        if (!signalList.isEmpty()) {
            signalProcessor.process(assetDataList.getFirst().getInfo(), signalList);
        }
    }

    protected List<Signal> signals(String ticker, CandleInterval interval, Indicator<Num> indicator) {

        var over = new OverIndicatorRule(indicator, 70);
        var under = new UnderIndicatorRule(indicator, 30);
        var crossUp30 = new CrossedUpIndicatorRule(indicator, 30);
        var crossUp70 = new CrossedUpIndicatorRule(indicator, 70);
        var crossDown70 = new CrossedDownIndicatorRule(indicator, 70);
        var crossDown30 = new CrossedDownIndicatorRule(indicator, 30);

        var waveDown = new ChainRule(crossDown30,
                new ChainLink(crossUp30, 10), new ChainLink(crossDown30, 10));
        var waveUp = new ChainRule(crossUp70,
                new ChainLink(crossDown70, 10), new ChainLink(crossUp70, 10));

        return List.of(
                Signal.builder()
                        .name("RSI > 70")
                        .interval(interval)
                        .ticker(ticker)
                        .direction(SELL)
                        .rule(over)
                        .build(),
                Signal.builder()
                        .name("RSI < 30")
                        .ticker(ticker)
                        .interval(interval)
                        .direction(BUY)
                        .rule(under)
                        .build(),
                Signal.builder()
                        .name("2x RSI >< 30")
                        .ticker(ticker)
                        .interval(interval)
                        .direction(SELL)
                        .rule(waveDown)
                        .build(),
                Signal.builder()
                        .name("2x RSI <> 70")
                        .ticker(ticker)
                        .interval(interval)
                        .direction(BUY)
                        .rule(waveUp)
                        .build()
        );
    }

}
