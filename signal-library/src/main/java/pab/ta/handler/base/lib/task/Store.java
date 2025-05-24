package pab.ta.handler.base.lib.task;

import java.util.List;
import java.util.function.Predicate;

public interface Store {

    /**
     * Save signals satisfied for rule.
     *
     * @param signal rule identity
     */
    void put(Signal signal);

    /**
     * Clear all data.
     */
    void clear();

    /**
     * Get signals.
     *
     * @return signals
     */
    List<Signal> get();

    /**
     * Get signals by condition.
     *
     * @param filter predicate
     * @return signals
     */
    List<Signal> get(Predicate<Signal> filter);
}
