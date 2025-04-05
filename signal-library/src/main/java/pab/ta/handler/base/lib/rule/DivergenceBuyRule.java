package pab.ta.handler.base.lib.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;
import org.ta4j.core.rules.UnderIndicatorRule;

public class DivergenceBuyRule extends AbstractRule {

    private final Indicator<Num> indicator;
    private final Indicator<Num> lowPrice;

    private final Rule indicatorUnderRule;
    private final Rule indicatorHollowRule;
    private final Rule lowPricePeakRule;
    private final int minIndex;

    public DivergenceBuyRule(Indicator<Num> indicator, Num minValue) {
        BarSeries series = indicator.getBarSeries();

        this.indicator = indicator;
        lowPrice = new LowPriceIndicator(series);

        indicatorHollowRule = new HollowRule3Bar(this.indicator);
        lowPricePeakRule = new HollowRule3Bar(lowPrice);

        indicatorUnderRule = new UnderIndicatorRule(indicator, minValue);

        int barCount = series.getBarCount();
        minIndex = barCount / 2;
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        if (index <= minIndex) {
            return false;
        }

        if (!(indicatorHollowRule.isSatisfied(index) && indicatorUnderRule.isSatisfied(index - 1) && lowPricePeakRule.isSatisfied(index))) {
            return false;
        }

        for (int i = index - 2; i > minIndex; i--) {
            if (indicatorHollowRule.isSatisfied(i) && indicatorUnderRule.isSatisfied(i - 1) && lowPricePeakRule.isSatisfied(i)) {

                if (lowPrice.getValue(index - 1).isLessThan(lowPrice.getValue(i - 1))
                        && indicator.getValue(index - 1).isGreaterThan(indicator.getValue(i - 1))) {
                    return true;
                }
            }
        }

        return false;
    }
}
