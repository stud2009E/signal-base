package pab.ta.handler.base.signal;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.CCIIndicator;
import org.ta4j.core.rules.UnderIndicatorRule;

import static pab.ta.handler.base.asset.Direction.BUY;

public class CciBuySignalProducer extends SignalProducer {

    public CciBuySignalProducer() {
        super("CCI < -100", BUY);
    }

    @Override
    protected Rule getRule(BarSeries series) {
        var indicator = new CCIIndicator(series, 14);

        return new UnderIndicatorRule(indicator, -100);
    }
}
