package pab.ta.handler.base.lib.signal;

import lombok.RequiredArgsConstructor;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.volume.MoneyFlowIndexIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
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
public class MfiSignalProducer implements AssetDataProcessor {

    private final SignalProcessor signalProcessor;

    @Override
    public void process(List<AssetData> assetDataList) {
        List<Signal> signalList = new LinkedList<>();

        assetDataList.forEach(assetData -> {
            var series = assetData.getBarSeries();

            Indicator<Num> indicator = new MoneyFlowIndexIndicator(series, 14);
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
        return List.of(
                Signal.builder()
                        .name("MFI > 80")
                        .ticker(ticker)
                        .interval(interval)
                        .direction(SELL)
                        .rule(new OverIndicatorRule(indicator, 80))
                        .build(),

                Signal.builder()
                        .name("MFI < 20")
                        .ticker(ticker)
                        .interval(interval)
                        .direction(BUY)
                        .rule(new UnderIndicatorRule(indicator, 20))
                        .build(),

                Signal.builder()
                        .name("MFI <> 20")
                        .ticker(ticker)
                        .interval(interval)
                        .direction(BUY)
                        .rule(new CrossedUpIndicatorRule(indicator, 20))
                        .build(),

                Signal.builder()
                        .name("MFI >< 80")
                        .ticker(ticker)
                        .interval(interval)
                        .direction(SELL)
                        .rule(new CrossedDownIndicatorRule(indicator, 80))
                        .build()
        );
    }

}
