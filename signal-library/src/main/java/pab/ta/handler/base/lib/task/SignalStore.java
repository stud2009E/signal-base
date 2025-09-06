package pab.ta.handler.base.lib.task;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pab.ta.handler.base.lib.signal.Signal;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@NoArgsConstructor
public class SignalStore implements IStore<Signal, String> {

    private final Set<Signal> cache = new HashSet<>();

    @Override
    public void put(Signal data) {
        //to update createdAt field of signal
        cache.remove(data);

        log.info("Signal {}, {}, {}, {}", data.getTicker(), data.getInterval().name(), data.getName(),
                data.getDirection().name());

        cache.add(data);
    }

    @Override
    public List<Signal> get(String key) {
        return cache.stream()
                .filter(signal -> signal.getTicker().equals(key))
                .toList();
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public List<Signal> getAll() {
        return cache.stream().toList();
    }
}
