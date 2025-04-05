package pab.ta.handler.base.signal;

import lombok.Setter;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import pab.ta.handler.base.asset.AssetType;
import pab.ta.handler.base.asset.CandleInterval;
import pab.ta.handler.base.asset.Direction;
import pab.ta.handler.base.asset.SeriesContainer;
import pab.ta.handler.base.task.BaseSignal;
import pab.ta.handler.base.task.Signal;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public abstract class SignalProducer {

    @Setter
    protected SeriesContainer container;
    protected String indicatorId;
    protected Direction direction;

    public SignalProducer(String indicatorId, Direction direction) {
        this.indicatorId = indicatorId;
        this.direction = direction;
    }

    public Optional<Signal> getSignal() {
        if (Objects.isNull(container)) {
            return Optional.empty();
        }

        return getSignal(container.getSeries().getEndIndex());
    }

    public Optional<Signal> getSignal(int index) {
        if (Objects.isNull(container)) {
            return Optional.empty();
        }

        String ticker = container.getAssetInfo().getTicker();
        AssetType type = container.getAssetInfo().getType();
        CandleInterval interval = container.getTimeFrame().getInterval();

        Signal signal = null;

        Rule rule = getRule(container.getSeries());

        if (rule.isSatisfied(index)) {
            signal = BaseSignal.builder()
                    .indicatorId(indicatorId)
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
