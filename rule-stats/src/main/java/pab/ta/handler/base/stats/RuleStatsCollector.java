package pab.ta.handler.base.stats;

import org.ta4j.core.BarSeries;

import java.util.ArrayList;
import java.util.List;

public class RuleStatsCollector {

    private final BaseRule rule;
    private final List<Integer> nextIndexes = new ArrayList<>();

    public RuleStatsCollector(BaseRule rule) {
        this.rule = rule;
        this.nextIndexes.addAll(List.of(1, 2, 3, 5));
    }

    public RuleStatsCollector(BaseRule rule, List<Integer> nextIndexes) {
        this.rule = rule;
        this.nextIndexes.addAll(nextIndexes);
    }

    public void process() {
        BarSeries series = rule.getBarSeries();
        List<RuleStat> ruleStatList = nextIndexes.stream()
                .map(index -> new RuleStat(rule, index))
                .toList();

        for (int i = series.getBeginIndex(); i < series.getEndIndex(); i++) {
            if (rule.isSatisfied(i)) {
                var j = i;
                ruleStatList.forEach(ruleStat -> ruleStat.calc(j));
            }
        }
    }

}
