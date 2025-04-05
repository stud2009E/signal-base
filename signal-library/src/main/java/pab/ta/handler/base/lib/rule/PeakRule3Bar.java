package pab.ta.handler.base.lib.rule;

import org.ta4j.core.Indicator;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;


public class PeakRule3Bar extends AbstractRule {

    private final Indicator<Num> indicator;

    public PeakRule3Bar(Indicator<Num> indicator) {
        this.indicator = indicator;
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        if (index < 2) {
            return false;
        }

        Num value1 = indicator.getValue(index);
        Num value0 = indicator.getValue(index - 1);
        Num value_1 = indicator.getValue(index - 2);

        return value0.isGreaterThan(value_1) && value0.isGreaterThan(value1);
    }
}
