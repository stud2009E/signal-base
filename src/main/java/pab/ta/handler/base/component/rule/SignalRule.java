package pab.ta.handler.base.component.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import pab.ta.handler.base.asset.AssetType;
import pab.ta.handler.base.asset.CandleInterval;
import pab.ta.handler.base.asset.Direction;
import pab.ta.handler.base.asset.SeriesContainer;
import pab.ta.handler.base.component.task.BaseSignal;
import pab.ta.handler.base.task.Signal;

import java.time.LocalDateTime;
import java.util.Optional;

public abstract class SignalRule {

    protected SeriesContainer container;
    protected String indicatorName;
    protected IndicatorGroup indicatorGroup;
    protected Direction direction;

    public SignalRule(SeriesContainer container, String indicatorName, IndicatorGroup indicatorGroup, Direction direction) {
        this.container = container;
        this.indicatorName = indicatorName;
        this.indicatorGroup = indicatorGroup;
        this.direction = direction;
    }

    public Optional<Signal> getSignal() {
        return getSignal(container.getSeries().getEndIndex());
    }

    public Optional<Signal> getSignal(int index) {
        String ticker = container.getAssetInfo().getTicker();
        AssetType type = container.getAssetInfo().getType();
        CandleInterval interval = container.getTimeFrame().getInterval();

        Signal signal = null;

        Rule rule = getRule(container.getSeries());

        if (rule.isSatisfied(index)) {
            signal = BaseSignal.builder()
                    .indicatorName(indicatorName)
                    .indicatorGroup(indicatorGroup)
                    .ticker(ticker)
                    .interval(interval)
                    .direction(direction)
                    .createdAt(LocalDateTime.now())
                    .type(type)
                    .build();
        }

        return Optional.ofNullable(signal);
    }

    protected abstract Rule getRule(BarSeries series);

}
