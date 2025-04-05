package pab.ta.handler.base.signal;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.CCIIndicator;
import org.ta4j.core.rules.OverIndicatorRule;

import static pab.ta.handler.base.asset.Direction.SELL;

public class CciSellSignalProducer extends SignalProducer {

    public CciSellSignalProducer() {
        super("CCI > 100", SELL);
    }

    @Override
    protected Rule getRule(BarSeries series) {
        var indicator = new CCIIndicator(series, 14);

        return new OverIndicatorRule(indicator, 100);
    }
}
