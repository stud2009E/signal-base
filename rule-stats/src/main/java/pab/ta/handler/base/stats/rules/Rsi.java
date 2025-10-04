package pab.ta.handler.base.stats.rules;

import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import pab.ta.handler.base.lib.asset.Direction;

public class Rsi {

    public static class CrossDown30 extends BaseRule {
        public CrossDown30(BarSeries barSeries) {
            super("RSI(14) >< 30", Direction.SELL, barSeries);
        }

        @Override
        public boolean isSatisfied(int index, TradingRecord tradingRecord) {
            var rsi = new RSIIndicator(new ClosePriceIndicator(getBarSeries()), 14);
            var rule30 = new CrossedDownIndicatorRule(rsi, 30);

            return rule30.isSatisfied(index);
        }
    }


    public static class CrossUp30 extends BaseRule {
        public CrossUp30(BarSeries barSeries) {
            super("RSI(14) <> 30", Direction.BUY, barSeries);
        }

        @Override
        public boolean isSatisfied(int index, TradingRecord tradingRecord) {
            var rsi = new RSIIndicator(new ClosePriceIndicator(getBarSeries()), 14);
            var rule30 = new CrossedUpIndicatorRule(rsi, 30);

            return rule30.isSatisfied(index);
        }
    }


    public static class CrossDown70 extends BaseRule {
        public CrossDown70(BarSeries barSeries) {
            super("RSI(14) >< 70", Direction.SELL, barSeries);
        }

        @Override
        public boolean isSatisfied(int index, TradingRecord tradingRecord) {
            var rsi = new RSIIndicator(new ClosePriceIndicator(getBarSeries()), 14);
            var rule30 = new CrossedDownIndicatorRule(rsi, 70);

            return rule30.isSatisfied(index);
        }
    }

    public static class CrossUp70 extends BaseRule {
        public CrossUp70(BarSeries barSeries) {
            super("RSI(14) <> 70", Direction.BUY, barSeries);
        }

        @Override
        public boolean isSatisfied(int index, TradingRecord tradingRecord) {
            var rsi = new RSIIndicator(new ClosePriceIndicator(getBarSeries()), 14);
            var rule30 = new CrossedUpIndicatorRule(rsi, 70);

            return rule30.isSatisfied(index);
        }
    }

}
