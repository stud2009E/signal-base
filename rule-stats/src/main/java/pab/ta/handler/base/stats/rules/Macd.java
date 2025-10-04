package pab.ta.handler.base.stats.rules;

import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import pab.ta.handler.base.lib.asset.Direction;

public class Macd {

    public static final int SHORT_LENGTH = 12;
    public static final int LONG_LENGTH = 26;

    public static class SignalCrossUp0 extends BaseRule {

        public SignalCrossUp0(BarSeries barSeries) {
            super("MACD <> 0", Direction.BUY, barSeries);
        }

        @Override
        public boolean isSatisfied(int index, TradingRecord tradingRecord) {
            var macd = new MACDIndicator(new ClosePriceIndicator(getBarSeries()), SHORT_LENGTH, LONG_LENGTH);
            var rule = new CrossedUpIndicatorRule(macd, 0);

            return rule.isSatisfied(index);
        }
    }


    public static class SignalCrossDown0 extends BaseRule {

        public SignalCrossDown0(BarSeries barSeries) {
            super("MACD >< 0", Direction.SELL, barSeries);
        }

        @Override
        public boolean isSatisfied(int index, TradingRecord tradingRecord) {
            var macd = new MACDIndicator(new ClosePriceIndicator(getBarSeries()), SHORT_LENGTH, LONG_LENGTH);
            var rule = new CrossedDownIndicatorRule(macd, 0);

            return rule.isSatisfied(index);

        }
    }

    public static class HistogramCrossUp0 extends BaseRule {

        public HistogramCrossUp0(BarSeries barSeries) {
            super("MACD hist <> 0", Direction.BUY, barSeries);
        }

        @Override
        public boolean isSatisfied(int index, TradingRecord tradingRecord) {
            var macd = new MACDIndicator(new ClosePriceIndicator(getBarSeries()), SHORT_LENGTH, LONG_LENGTH);
            var rule = new CrossedUpIndicatorRule(macd.getHistogram(SHORT_LENGTH), 0);

            return rule.isSatisfied(index);
        }
    }

    public static class HistogramCrossDown0 extends BaseRule {

        public HistogramCrossDown0(BarSeries barSeries) {
            super("MACD hist >< 0", Direction.SELL, barSeries);
        }

        @Override
        public boolean isSatisfied(int index, TradingRecord tradingRecord) {
            var macd = new MACDIndicator(new ClosePriceIndicator(getBarSeries()), SHORT_LENGTH, LONG_LENGTH);
            var rule = new CrossedDownIndicatorRule(macd.getHistogram(SHORT_LENGTH), 0);

            return rule.isSatisfied(index);
        }
    }


}
