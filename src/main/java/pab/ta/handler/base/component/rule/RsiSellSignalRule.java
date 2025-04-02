package pab.ta.handler.base.component.rule;

import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.rules.UnderIndicatorRule;
import pab.ta.handler.base.asset.AssetType;
import pab.ta.handler.base.asset.CandleInterval;
import pab.ta.handler.base.asset.SeriesContainer;
import pab.ta.handler.base.component.task.BaseSignal;
import pab.ta.handler.base.task.Signal;

import java.time.LocalDateTime;
import java.util.Optional;

import static pab.ta.handler.base.asset.Direction.SELL;
import static pab.ta.handler.base.component.rule.IndicatorGroup.RSI;

public class RsiSellSignalRule {

    private final SeriesContainer container;
    private final UnderIndicatorRule sellRule;

    public RsiSellSignalRule(SeriesContainer seriesContainer) {
        var indicator = new RSIIndicator(new ClosePriceIndicator(seriesContainer.getSeries()), 14);

        container = seriesContainer;
        sellRule = new UnderIndicatorRule(indicator, 30);
    }

    public Optional<Signal> getSignal() {
        return getSignal(container.getSeries().getEndIndex());
    }

    public Optional<Signal> getSignal(int index) {
        String ticker = container.getAssetInfo().getTicker();
        AssetType type = container.getAssetInfo().getType();
        CandleInterval interval = container.getTimeFrame().getInterval();

        Signal signal = null;

        if (sellRule.isSatisfied(index)) {
            signal = BaseSignal.builder()
                    .indicatorName("RSI < 30")
                    .indicatorGroup(RSI)
                    .ticker(ticker)
                    .interval(interval)
                    .createdAt(LocalDateTime.now())
                    .direction(SELL)
                    .type(type)
                    .build();
        }

        return Optional.ofNullable(signal);
    }
}
