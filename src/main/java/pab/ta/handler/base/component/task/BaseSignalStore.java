package pab.ta.handler.base.component.task;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pab.ta.handler.base.asset.*;
import pab.ta.handler.base.component.rule.RuleGroup;
import pab.ta.handler.base.task.Signal;
import pab.ta.handler.base.task.SignalSelector;
import pab.ta.handler.base.task.SignalStore;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BaseSignalStore implements SignalStore {

    @Value("${signal.live-time-minutes:30}")
    @Setter
    private Long signalLiveTime;

    private final Set<Signal> cache = new HashSet<>();

    @Override
    public boolean put(RuleIdentity ruleIdentity) {

        AssetInfo info = ruleIdentity.seriesIdentity().info();
        TimeFrame tf = ruleIdentity.seriesIdentity().tf();

        Signal signal = BaseSignal.builder()
                .ticker(info.ticker())
                .interval(tf.interval())
                .direction(ruleIdentity.direction())
                .ruleId(ruleIdentity.id())
                .direction(ruleIdentity.direction())
                .type(info.type())
                .ruleGroup(ruleIdentity.group())
                .createdAt(LocalDateTime.now())
                .build();

        //to update createdAt field of signal
        cache.remove(signal);

        return cache.add(signal);
    }

    @Override
    public Set<String> findTicker(SignalSelector.SignalFilter filter) {
        Set<String> result = new HashSet<>();
        boolean isFirst = true;

        for (CandleInterval interval : filter.rules().keySet()) {
            for (RuleGroup ruleGroup : filter.rules().get(interval)) {
                Set<String> temp = filterSignalsBy(ruleGroup, interval, filter.direction());

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
    private Set<String> filterSignalsBy(RuleGroup ruleGroup, CandleInterval interval, Direction direction) {
        LocalDateTime now = LocalDateTime.now();

        return cache.parallelStream()
                .filter(signal -> now.minusMinutes(signalLiveTime).isBefore(signal.getCreatedAt()))
                .filter(signal -> signal.getRuleGroup().equals(ruleGroup) &&
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
