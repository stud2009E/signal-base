package pab.ta.handler.base.lib.indicator;

import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.AbstractIndicator;
import org.ta4j.core.num.DecimalNum;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.OverIndicatorRule;
import pab.ta.handler.base.lib.rule.PeakRule3Bar;

import java.util.ArrayList;
import java.util.List;

public class AvgMaxIndicator extends AbstractIndicator<Num> {
    private final Indicator<Num> indicator;
    private final Num upBorder;

    Rule overRule;
    Rule peakRule;

    public AvgMaxIndicator(Indicator<Num> indicator, Num upBorder) {
        super(indicator.getBarSeries());

        this.indicator = indicator;
        this.upBorder = upBorder;

        overRule = new OverIndicatorRule(indicator, upBorder);
        peakRule = new PeakRule3Bar(indicator);
    }

    @Override
    public Num getValue(int index) {
        List<Double> maxValues = new ArrayList<>();

        for (int i = index; i > 0; i--) {
            if (overRule.isSatisfied(i - 1) && peakRule.isSatisfied(i)) {
                maxValues.add(indicator.getValue(i - 1).doubleValue());
            }
        }

        if (maxValues.isEmpty()) {
            return upBorder;
        }

        if (maxValues.size() == 1) {
            double delta = (maxValues.getFirst() - upBorder.doubleValue()) / 10;
            return DecimalNum.valueOf(maxValues.getFirst() - delta);
        }

        maxValues.sort((a, b) -> -(int) (a - b));

        return DecimalNum.valueOf((maxValues.get(0) + maxValues.get(1)) / 2);
    }

    @Override
    public int getUnstableBars() {
        return 0;
    }
}