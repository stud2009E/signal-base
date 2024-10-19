package pab.ta.handler.base.indicator;

import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.AbstractIndicator;
import org.ta4j.core.num.DecimalNum;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.UnderIndicatorRule;
import pab.ta.handler.base.rule.HollowRule3Bar;

import java.util.ArrayList;
import java.util.List;

public class AvgMinIndicator extends AbstractIndicator<Num> {
    private final Indicator<Num> indicator;
    private final Num lowBorder;

    Rule underRule;
    Rule hollowRule;

    public AvgMinIndicator(Indicator<Num> indicator, Num lowBorder){
        super(indicator.getBarSeries());

        this.indicator = indicator;
        this.lowBorder = lowBorder;

        underRule = new UnderIndicatorRule(indicator, lowBorder);
        hollowRule = new HollowRule3Bar(indicator);
    }

    @Override
    public Num getValue(int index) {
        List<Double> minValues = new ArrayList<>();

        for (int i = index; i > 0; i--) {
            if(underRule.isSatisfied(i - 1) && hollowRule.isSatisfied(i)){
                minValues.add(indicator.getValue(i-1).doubleValue());
            }
        }

        if (minValues.isEmpty()){
            return lowBorder;
        }

        if (minValues.size() == 1){
            minValues.add(lowBorder.doubleValue());
        }

        Double avg =  minValues.stream()
                .mapToDouble(value -> value)
                .average()
                .orElseGet(lowBorder::doubleValue);

        return DecimalNum.valueOf(avg);
    }

    @Override
    public int getUnstableBars() {
        return 0;
    }
}