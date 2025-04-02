package pab.ta.handler.base.component.task;

import com.google.common.eventbus.Subscribe;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pab.ta.handler.base.asset.Direction;
import pab.ta.handler.base.asset.TimeFrame;
import pab.ta.handler.base.task.SignalSelector;
import pab.ta.handler.base.task.SignalStore;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static pab.ta.handler.base.asset.CandleInterval.*;
import static pab.ta.handler.base.component.rule.IndicatorGroup.CCI;
import static pab.ta.handler.base.component.rule.IndicatorGroup.RSI;

@Component
@RequiredArgsConstructor
public class BaseSignalSelector implements SignalSelector {

    private final SignalStore store;
    private final BaseTaskHandler handler;

    @PostConstruct
    public void initialize() {
        handler.register(this);
    }

    @Override
    public List<SignalFilter> getFilters() {
        return List.of(
                new SignalFilter(
                        Map.of(
                                HOUR_4, List.of(RSI, CCI),
                                DAY, List.of(RSI, CCI),
                                WEEK, List.of(RSI, CCI)),
                        Direction.SELL
                ),
                new SignalFilter(
                        Map.of(
                                HOUR_4, List.of(RSI, CCI),
                                DAY, List.of(RSI, CCI),
                                WEEK, List.of(RSI, CCI)),
                        Direction.SELL
                )
        );
    }

    @Override
    @Subscribe
    public void selectOnEvent(TimeFrame tf) {
        getFilters()
                .stream()
                .filter(filter -> filter.rules().containsKey(tf.getInterval()))
                .forEach(filter -> {
                    Set<String> result = store.findTicker(filter);
                    logResult(result, filter);
                });
    }
}