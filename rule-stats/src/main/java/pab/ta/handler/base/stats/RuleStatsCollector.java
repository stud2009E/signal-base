package pab.ta.handler.base.stats;

import lombok.Getter;
import pab.ta.handler.base.lib.asset.Direction;
import pab.ta.handler.base.stats.counter.BearPredicate;
import pab.ta.handler.base.stats.counter.BullPredicate;
import pab.ta.handler.base.stats.counter.StatsCounter;
import pab.ta.handler.base.stats.rules.BaseRule;

import java.util.List;
import java.util.function.Predicate;

@Getter
public class RuleStatsCollector {

    private final BaseRule rule;
    private final List<StatsCounter> counters;

    public RuleStatsCollector(BaseRule rule) {
        this(rule, List.of(1, 2, 3, 5));
    }

    public RuleStatsCollector(BaseRule rule, List<Integer> testIndexes) {
        this.rule = rule;

        counters = testIndexes
                .stream()
                .map(StatsCounter::new)
                .toList();
    }

    public void process() {
        var series = rule.getBarSeries();

        for (int i = series.getBeginIndex(); i < series.getEndIndex(); i++) {
            if (rule.isSatisfied(i)) {
                var predicate = getPredicate(i);

                for (var counter : counters) {
                    counter.setPredicate(predicate);
                    counter.write(i);
                }
            }
        }
    }


    private Predicate<Integer> getPredicate(Integer signalIndex) {
        Predicate<Integer> predicate = i -> false;

        if (Direction.SELL == rule.getDirection()) {
            predicate = new BearPredicate(rule.getBarSeries(), signalIndex);
        }

        if (Direction.BUY == rule.getDirection()) {
            predicate = new BullPredicate(rule.getBarSeries(), signalIndex);
        }

        return predicate;
    }


}
