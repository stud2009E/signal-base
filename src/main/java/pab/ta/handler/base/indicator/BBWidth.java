package pab.ta.handler.base.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.AbstractIndicator;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsLowerIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsMiddleIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsUpperIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.num.Num;

public class BBWidth extends AbstractIndicator<Num> {

    private final BollingerBandsLowerIndicator low;
    private final BollingerBandsUpperIndicator up;
    private final int barCount;

    public BBWidth(BarSeries series, int barCount) {
        super(series);

        this.barCount = barCount;

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        EMAIndicator avg = new EMAIndicator(closePrice, barCount);
        StandardDeviationIndicator sd = new StandardDeviationIndicator(closePrice, barCount);

        BollingerBandsMiddleIndicator middle = new BollingerBandsMiddleIndicator(avg);
        low = new BollingerBandsLowerIndicator(middle, sd);
        up = new BollingerBandsUpperIndicator(middle, sd);
    }

    @Override
    public Num getValue(int index) {
        return up.getValue(index).minus(low.getValue(index));
    }

    @Override
    public int getUnstableBars() {
        return this.barCount;
    }
}
