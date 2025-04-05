package pab.ta.handler.base.lib.task;

import lombok.RequiredArgsConstructor;
import pab.ta.handler.base.lib.asset.CandleInterval;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BaseSignalStore implements SignalStore {

    private final Long lifetimeInMinutes;

    private final Set<Signal> cache = new HashSet<>();

    @Override
    public void put(Signal signal) {
        //to update createdAt field of signal
        cache.remove(signal);

        cache.add(signal);
    }

    @Override
    public Set<String> getTickers(Map<CandleInterval, List<String>> filter) {
        Set<String> result = new HashSet<>();
        boolean isFirst = true;

        for (var entry : filter.entrySet()) {
            Set<String> temp = filterTickersBy(entry.getKey(), entry.getValue());

            if (isFirst) {
                isFirst = false;
                result.addAll(temp);
            }

            result.retainAll(temp);
        }

        return result;
    }

    /**
     * filter signals by params and creation time (only fresh signals)
     *
     * @param interval     candle interval
     * @param indicatorIds indicator list
     * @return tickers
     */
    private Set<String> filterTickersBy(CandleInterval interval, List<String> indicatorIds) {
        LocalDateTime timeEdge = LocalDateTime.now().minusMinutes(lifetimeInMinutes);

        return cache.parallelStream()
                .filter(signal -> timeEdge.isBefore(signal.getCreatedAt()))
                .filter(signal -> signal.getInterval().equals(interval)
                        && indicatorIds.contains(signal.getIndicatorId())
                )
                .map(Signal::getTicker)
                .collect(Collectors.toSet());
    }

}
