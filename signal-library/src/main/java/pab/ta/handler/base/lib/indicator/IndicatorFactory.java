package pab.ta.handler.base.lib.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.*;
import org.ta4j.core.indicators.adx.ADXIndicator;
import org.ta4j.core.indicators.averages.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.volume.MoneyFlowIndexIndicator;
import org.ta4j.core.indicators.volume.VWAPIndicator;
import org.ta4j.core.num.Num;

/**
 * Indicator factory.
 */
public class IndicatorFactory {

    /**
     * Get instance of indicator.
     *
     * @param indicator indicator type
     * @param series    data
     * @return instance
     */
    public static AbstractIndicator<Num> getInstance(SupportedIndicator indicator, BarSeries series) {
        return switch (indicator) {
            case MA50 -> new SMAIndicator(new ClosePriceIndicator(series), 50);
            case MA100 -> new SMAIndicator(new ClosePriceIndicator(series), 100);
            case MA200 -> new SMAIndicator(new ClosePriceIndicator(series), 200);
            case RSI14 -> new RSIIndicator(new ClosePriceIndicator(series), 14);
            case MFI14 -> new MoneyFlowIndexIndicator(series, 14);
            case CCI14 -> new CCIIndicator(series, 14);
            case ATR14 -> new ATRIndicator(series, 14);
            case ADX14 -> new ADXIndicator(series, 14);
            case MACD -> new MACDIndicator(new ClosePriceIndicator(series));
            case VWAP -> new VWAPIndicator(series, 14);
        };
    }
}
