package pab.ta.handler.base.lib.indicator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.ATRIndicator;
import org.ta4j.core.indicators.CCIIndicator;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.adx.ADXIndicator;
import org.ta4j.core.indicators.adx.MinusDIIndicator;
import org.ta4j.core.indicators.adx.PlusDIIndicator;
import org.ta4j.core.indicators.averages.SMAIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsLowerIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsMiddleIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsUpperIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.numeric.NumericIndicator;
import org.ta4j.core.indicators.volume.MoneyFlowIndexIndicator;
import org.ta4j.core.indicators.volume.VWAPIndicator;
import org.ta4j.core.num.Num;

/**
 * Indicator factory.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IndicatorFactory {

    /**
     * Get instance of indicator.
     *
     * @param indicator indicator type
     * @param series    data
     * @return instance
     */
    public static Indicator<Num> getInstance(IndicatorType indicator, BarSeries series) {
        var closePrice = new ClosePriceIndicator(series);
        var numericClosePrice = NumericIndicator.of(closePrice);

        return switch (indicator) {
            case MA50 -> new SMAIndicator(closePrice, 50);
            case MA100 -> new SMAIndicator(closePrice, 100);
            case MA200 -> new SMAIndicator(closePrice, 200);

            case RSI14 -> new RSIIndicator(closePrice, 14);
            case MFI14 -> new MoneyFlowIndexIndicator(series, 14);
            case CCI14 -> new CCIIndicator(series, 14);

            case ATR14 -> new ATRIndicator(series, 14);

            case ADX14 -> new ADXIndicator(series, 14);
            case ADX_MINUS14 -> new MinusDIIndicator(series, 14);
            case ADX_PLUS14 -> new PlusDIIndicator(series, 14);

            case MACD -> new MACDIndicator(closePrice);
            case VWAP -> new VWAPIndicator(series, 14);

            case BB_LOW -> new BollingerBandsLowerIndicator(
                    new BollingerBandsMiddleIndicator(
                            numericClosePrice.sma(20)), numericClosePrice.stddev(20));
            case BB_UP -> new BollingerBandsUpperIndicator(
                    new BollingerBandsMiddleIndicator(
                            numericClosePrice.sma(20)), numericClosePrice.stddev(20));
        };
    }
}
