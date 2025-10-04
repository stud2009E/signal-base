package pab.ta.handler.base.stats.rules;

import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.volume.MoneyFlowIndexIndicator;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import pab.ta.handler.base.lib.asset.Direction;

public class Mfi {

    public static class CrossDown20 extends BaseRule {
        public CrossDown20(BarSeries barSeries) {
            super("MFI(14) >< 20", Direction.SELL, barSeries);
        }

        @Override
        public boolean isSatisfied(int index, TradingRecord tradingRecord) {
            var mfi = new MoneyFlowIndexIndicator(getBarSeries(), 14);
            var rule = new CrossedDownIndicatorRule(mfi, 20);

            return rule.isSatisfied(index);
        }
    }


    public static class CrossUp20 extends BaseRule {
        public CrossUp20(BarSeries barSeries) {
            super("MFI(14) <> 20", Direction.BUY, barSeries);
        }

        @Override
        public boolean isSatisfied(int index, TradingRecord tradingRecord) {
            var mfi = new MoneyFlowIndexIndicator(getBarSeries(), 14);
            var rule = new CrossedUpIndicatorRule(mfi, 20);

            return rule.isSatisfied(index);
        }
    }


    public static class CrossDown80 extends BaseRule {
        public CrossDown80(BarSeries barSeries) {
            super("MFI(14) >< 80", Direction.SELL, barSeries);
        }

        @Override
        public boolean isSatisfied(int index, TradingRecord tradingRecord) {
            var mfi = new MoneyFlowIndexIndicator(getBarSeries(), 14);
            var rule = new CrossedDownIndicatorRule(mfi, 80);

            return rule.isSatisfied(index);
        }
    }

    public static class CrossUp80 extends BaseRule {
        public CrossUp80(BarSeries barSeries) {
            super("MFI(14) <> 80", Direction.BUY, barSeries);
        }

        @Override
        public boolean isSatisfied(int index, TradingRecord tradingRecord) {
            var mfi = new MoneyFlowIndexIndicator(getBarSeries(), 14);
            var rule = new CrossedUpIndicatorRule(mfi, 80);

            return rule.isSatisfied(index);
        }
    }

}
