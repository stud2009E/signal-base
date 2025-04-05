package pab.ta.handler.base.lib.rule;

import org.ta4j.core.Rule;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.rules.AbstractRule;


public class RepeatRule extends AbstractRule {
    private final int barLength;
    private final int satisfiedCount;

    private final Rule rule;

    public RepeatRule(Rule rule, int satisfiedCount, int barLength) {
        this.rule = rule;
        this.barLength = barLength;
        this.satisfiedCount = satisfiedCount;

        if (satisfiedCount < 1 || barLength < 1){
            throw new IllegalArgumentException("satisfiedCount or barLength < 1");
        }
    }

    public RepeatRule(Rule rule, int barLength) {
        this(rule, 2, barLength);
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        int count = 0;

        if(!rule.isSatisfied(index)){
            return false;
        }

        for (int i = index; i + barLength > index && i > 0 ; i--){
            if(rule.isSatisfied(i)){
                count++;
            }
        }

        return this.satisfiedCount <= count;
    }
}
