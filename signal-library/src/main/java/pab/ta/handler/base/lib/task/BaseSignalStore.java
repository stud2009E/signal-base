package pab.ta.handler.base.lib.task;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class BaseSignalStore implements Store {

    private final Set<Signal> cache = new HashSet<>();

    @Override
    public void put(Signal signal) {
        //to update createdAt field of signal
        cache.remove(signal);

        cache.add(signal);
    }

    @Override
    public void clear() {
        cache.clear();
    }


    @Override
    public List<Signal> get() {
        return cache.stream().toList();
    }

    @Override
    public List<Signal> get(Predicate<Signal> filter) {
        return cache.stream().filter(filter).toList();
    }

}
