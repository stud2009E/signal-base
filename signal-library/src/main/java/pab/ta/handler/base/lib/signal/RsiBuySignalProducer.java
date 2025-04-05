package pab.ta.handler.base.lib.signal;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.rules.OverIndicatorRule;

import static pab.ta.handler.base.lib.asset.Direction.BUY;

public class RsiBuySignalProducer extends SignalProducer {

    public RsiBuySignalProducer() {
        super("RSI < 30", BUY);
    }

    @Override
    protected Rule getRule(BarSeries series) {
        var indicator = new RSIIndicator(new ClosePriceIndicator(series), 14);

        return new OverIndicatorRule(indicator, 30);
    }
}
