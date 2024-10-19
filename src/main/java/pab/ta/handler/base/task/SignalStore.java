package pab.ta.handler.base.task;

import pab.ta.handler.base.asset.RuleIdentity;

import java.util.Set;

public interface SignalStore {

    /**
     * save signals satisfied for rule
     *
     * @param ruleIdentity rule identity
     * @return search new signal result
     */
    boolean put(RuleIdentity ruleIdentity);

    /**
     * find tickers by direction, interval and rule group
     *
     * @param filter filter by interval, direction, and rule group
     * @return tickers
     */
    Set<String> findTicker(SignalSelector.SignalFilter filter);


    /**
     * get signals by ticker
     *
     * @param ticker ticker
     * @return signals
     */
    Set<Signal> findSignal(String ticker);
}
