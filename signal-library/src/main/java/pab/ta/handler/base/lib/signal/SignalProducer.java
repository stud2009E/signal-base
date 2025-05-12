package pab.ta.handler.base.lib.signal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import pab.ta.handler.base.lib.asset.AssetType;
import pab.ta.handler.base.lib.asset.CandleInterval;
import pab.ta.handler.base.lib.asset.Direction;
import pab.ta.handler.base.lib.asset.SeriesContainer;
import pab.ta.handler.base.lib.task.BaseSignal;
import pab.ta.handler.base.lib.task.Signal;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public abstract class SignalProducer {

    private final String indicatorId;
    private final Direction direction;

    public Optional<Signal> getSignal(SeriesContainer container) {
        return getSignal(container, container.getSeries().getEndIndex());
    }

    public Optional<Signal> getSignal(SeriesContainer container, int index) {
        if (Objects.isNull(container)) {
            return Optional.empty();
        }

        String ticker = container.getAssetInfo().getTicker();
        AssetType type = container.getAssetInfo().getType();
        CandleInterval interval = container.getTimeFrame().getInterval();

        Signal signal = null;

        Rule rule = getRule(container.getSeries());

        if (rule.isSatisfied(index)) {
            signal = BaseSignal.builder().
                    indicatorId(indicatorId).
                    ticker(ticker).
                    interval(interval).
                    direction(direction).
                    createdAt(LocalDateTime.now())
                    .type(type)
                    .build();
        }

        return Optional.ofNullable(signal);
    }

    protected abstract Rule getRule(BarSeries series);

}
