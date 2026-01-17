package pab.ta.handler.base.lib.signal;

import lombok.RequiredArgsConstructor;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsMiddleIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsUpperIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.numeric.NumericIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import org.ta4j.core.rules.OverIndicatorRule;
import pab.ta.handler.base.lib.asset.AssetData;
import pab.ta.handler.base.lib.asset.CandleInterval;
import pab.ta.handler.base.lib.task.AssetDataProcessor;

import java.util.LinkedList;
import java.util.List;

import static pab.ta.handler.base.lib.asset.Direction.SELL;

@RequiredArgsConstructor
public class BBUpSignalProducer implements AssetDataProcessor {

    private final SignalProcessor signalProcessor;

    @Override
    public void process(List<AssetData> assetDataList) {
        List<Signal> signalList = new LinkedList<>();

        assetDataList
                .forEach(assetData -> {
                    var series = assetData.getBarSeries();
                    var closePrice = new ClosePriceIndicator(series);
                    var numericClosePrice = NumericIndicator.of(closePrice);

                    var indicator = new BollingerBandsUpperIndicator(
                            new BollingerBandsMiddleIndicator(
                                    numericClosePrice.sma(20)), numericClosePrice.stddev(20));

                    signals(assetData.getTicker(), assetData.getInterval(), indicator, closePrice)
                            .stream()
                            .filter(signal -> signal.getRule().isSatisfied(series.getEndIndex()))
                            .forEach(signalList::add);
                });

        if (!signalList.isEmpty()) {
            signalProcessor.process(assetDataList.getFirst().getInfo(), signalList);
        }
    }

    protected List<Signal> signals(String ticker, CandleInterval interval,
                                   Indicator<Num> indicator, ClosePriceIndicator closePrice) {
        return List.of(
                Signal.builder()
                        .name("BB_UP < price")
                        .ticker(ticker)
                        .interval(interval)
                        .direction(SELL)
                        .rule(new OverIndicatorRule(closePrice, indicator))
                        .build(),
                Signal.builder()
                        .name("BB_UP >< price")
                        .ticker(ticker)
                        .interval(interval)
                        .direction(SELL)
                        .rule(new CrossedUpIndicatorRule(closePrice, indicator))
                        .build(),
                Signal.builder()
                        .name("BB_UP <> price")
                        .ticker(ticker)
                        .interval(interval)
                        .direction(SELL)
                        .rule(new CrossedDownIndicatorRule(closePrice, indicator))
                        .build());
    }
}
