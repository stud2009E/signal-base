package pab.ta.handler.base.component.task;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import pab.ta.handler.base.asset.CandleInterval;
import pab.ta.handler.base.asset.Direction;
import pab.ta.handler.base.component.rule.IndicatorGroup;
import pab.ta.handler.base.task.Signal;
import pab.ta.handler.base.task.SignalSelector;
import pab.ta.handler.base.task.SignalStore;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class BaseSignalStore implements SignalStore {

    @Value("${signal.live-time-minutes:30}")
    @Setter
    private Long signalLiveTime;

    private final Set<Signal> cache = new HashSet<>();

    @Override
    public boolean put(Signal signal) {
        //to update createdAt field of signal
        cache.remove(signal);

        return cache.add(signal);
    }

    @Override
    public Set<String> findTicker(SignalSelector.SignalFilter filter) {
        Set<String> result = new HashSet<>();
        boolean isFirst = true;

        for (CandleInterval interval : filter.rules().keySet()) {
            for (IndicatorGroup group : filter.rules().get(interval)) {
                Set<String> temp = filterSignalsBy(group, interval, filter.direction());

                if (isFirst) {
                    isFirst = false;
                    result.addAll(temp);
                }

                result.retainAll(temp);
            }
        }

        return result;
    }

    /**
     * filter signals by params and creation time (only fresh signals)
     *
     * @param ruleGroup rule group (CCI, RSI, MFI)
     * @param interval  candle interval
     * @param direction BUY or SELL
     * @return tickers
     */
    private Set<String> filterSignalsBy(IndicatorGroup ruleGroup, CandleInterval interval, Direction direction) {
        LocalDateTime now = LocalDateTime.now();

        return cache.parallelStream()
                .filter(signal -> now.minusMinutes(signalLiveTime).isBefore(signal.getCreatedAt()))
                .filter(signal -> signal.getIndicatorGroup().equals(ruleGroup) &&
                        signal.getInterval().equals(interval) &&
                        signal.getDirection().equals(direction)
                )
                .map(Signal::getTicker)
                .collect(Collectors.toSet());
    }


    @Override
    public Set<Signal> findSignal(String ticker) {
        return cache
                .parallelStream()
                .filter(signal -> signal.getTicker().equals(ticker))
                .collect(Collectors.toSet());
    }

}
