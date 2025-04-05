package pab.ta.handler.base.signal;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.volume.MoneyFlowIndexIndicator;
import org.ta4j.core.rules.UnderIndicatorRule;

import static pab.ta.handler.base.asset.Direction.BUY;

public class MfiBuySignalProducer extends SignalProducer {

    public MfiBuySignalProducer() {
        super("MFI < 20", BUY);
    }

    @Override
    protected Rule getRule(BarSeries series) {
        var indicator = new MoneyFlowIndexIndicator(series, 14);

        return new UnderIndicatorRule(indicator, 20);
    }
}
