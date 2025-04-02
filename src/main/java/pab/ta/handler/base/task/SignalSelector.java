package pab.ta.handler.base.task;

import com.google.common.base.Strings;
import pab.ta.handler.base.asset.CandleInterval;
import pab.ta.handler.base.asset.Direction;
import pab.ta.handler.base.asset.TimeFrame;
import pab.ta.handler.base.component.rule.IndicatorGroup;

import java.util.*;

public interface SignalSelector {

    record SignalFilter(Map<CandleInterval, List<IndicatorGroup>> rules, Direction direction) {
    }

    void selectOnEvent(TimeFrame tf);

    List<SignalFilter> getFilters();

    default void logResult(Set<String> result, SignalFilter filter) {
        if (result.isEmpty()) {
            return;
        }

        System.out.println(Strings.repeat("-", 30) + new Date() + Strings.repeat("-", 30));
        System.out.println(Strings.repeat(">", 5) + filter.rules().keySet());
        System.out.println(Strings.repeat(">", 5) + filter.direction());
        System.out.println(Strings.repeat(">", 5) + new HashSet<>(filter.rules().values()));
        System.out.println(result);
        System.out.println(Strings.repeat("-", new Date().toString().length() + 60));
    }
}