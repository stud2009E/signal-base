package pab.ta.handler.base.lib.signal;

import lombok.RequiredArgsConstructor;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import pab.ta.handler.base.lib.asset.AssetData;
import pab.ta.handler.base.lib.asset.CandleInterval;
import pab.ta.handler.base.lib.task.AssetDataProcessor;

import java.util.LinkedList;
import java.util.List;

import static pab.ta.handler.base.lib.asset.Direction.BUY;
import static pab.ta.handler.base.lib.asset.Direction.SELL;

@RequiredArgsConstructor
public class MacdSignalProducer implements AssetDataProcessor {

    private final SignalProcessor signalProcessor;

    @Override
    public void process(List<AssetData> assetDataList) {
        List<Signal> signalList = new LinkedList<>();

        assetDataList
                .forEach(assetData -> {
                    var series = assetData.getBarSeries();
                    var closePrice = new ClosePriceIndicator(series);

                    var indicator = new MACDIndicator(closePrice).getHistogram(9);
                    var index = series.getEndIndex();

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

        return List.of(
                Signal.builder()
                        .name("MACD hist <> 0")
                        .interval(interval)
                        .ticker(ticker)
                        .direction(BUY)
                        .rule(new CrossedUpIndicatorRule(indicator, 0))
                        .build(),
                Signal.builder()
                        .name("MACD hist >< 0")
                        .interval(interval)
                        .ticker(ticker)
                        .direction(SELL)
                        .rule(new CrossedDownIndicatorRule(indicator, 0))
                        .build()
        );
    }
}
