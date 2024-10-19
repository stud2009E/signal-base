package pab.ta.handler.base.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;
import org.ta4j.core.rules.OverIndicatorRule;

public class DivergenceSellRule extends AbstractRule {

    private final Indicator<Num> indicator;
    private final Indicator<Num> highPrice;

    private final Rule indicatorOverRule;
    private final Rule indicatorPeakRule;
    private final Rule highPricePeakRule;
    private final int minIndex;

    public DivergenceSellRule(Indicator<Num> indicator, Num minValue) {
        BarSeries series = indicator.getBarSeries();

        this.indicator = indicator;
        highPrice = new HighPriceIndicator(series);

        indicatorPeakRule = new PeakRule3Bar(this.indicator);
        highPricePeakRule = new PeakRule3Bar(highPrice);

        indicatorOverRule = new OverIndicatorRule(indicator, minValue);

        int barCount = series.getBarCount();
        minIndex = barCount / 2;
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        if (index <= minIndex) {
            return false;
        }

        if (!(indicatorPeakRule.isSatisfied(index) && indicatorOverRule.isSatisfied(index - 1) && highPricePeakRule.isSatisfied(index))) {
            return false;
        }

        for (int i = index - 2; i > minIndex; i--) {
            if (indicatorPeakRule.isSatisfied(i) && indicatorOverRule.isSatisfied(i - 1) && highPricePeakRule.isSatisfied(i)) {

                if (highPrice.getValue(index - 1).isGreaterThan(highPrice.getValue(i - 1))
                        && indicator.getValue(index - 1).isLessThan(indicator.getValue(i - 1))) {
                    return true;
                }
            }
        }

        return false;
    }
}
