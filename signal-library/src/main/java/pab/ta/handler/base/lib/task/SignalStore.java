package pab.ta.handler.base.lib.task;

import pab.ta.handler.base.lib.asset.CandleInterval;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SignalStore {

    /**
     * save signals satisfied for rule
     *
     * @param signal rule identity
     */
    void put(Signal signal);

    /**
     * find tickers
     *
     * @param filter filter
     * @return tickers
     */
    Set<String> getTickers(Map<CandleInterval, List<String>> filter);
}
