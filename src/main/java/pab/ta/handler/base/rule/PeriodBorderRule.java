package pab.ta.handler.base.rule;

import org.ta4j.core.Rule;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.rules.AbstractRule;

import java.util.ArrayList;
import java.util.List;

public class PeriodBorderRule extends AbstractRule {

    private final List<Rule> rules = new ArrayList<>();
    private final int barLength;

    public PeriodBorderRule(List<Rule> rules, int barLength) {
        this.rules.addAll(rules);
        this.barLength = barLength;

        if (barLength < 1) {
            throw new IllegalArgumentException("barLength < 1");
        }
    }

    public PeriodBorderRule(Rule rule, int barLength) {
        this(List.of(rule), barLength);
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {

        return rules.stream().allMatch(rule -> {
            boolean isSatisfied = false;

            for (int i = index; i + barLength > index && i > 0; i--) {
                if (rule.isSatisfied(i)) {
                    isSatisfied = true;
                    break;
                }
            }

            return isSatisfied;
        });

    }
}
