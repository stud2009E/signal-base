package pab.ta.handler.base.signal;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.rules.OverIndicatorRule;

import static pab.ta.handler.base.asset.Direction.SELL;

public class RsiSellSignalProducer extends SignalProducer {

    public RsiSellSignalProducer() {
        super("RSI > 70", SELL);
    }

    @Override
    protected Rule getRule(BarSeries series) {
        var indicator = new RSIIndicator(new ClosePriceIndicator(series), 14);

        return new OverIndicatorRule(indicator, 70);
    }
}
