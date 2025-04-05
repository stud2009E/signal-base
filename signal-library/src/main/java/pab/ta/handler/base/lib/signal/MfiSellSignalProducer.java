package pab.ta.handler.base.lib.signal;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.volume.MoneyFlowIndexIndicator;
import org.ta4j.core.rules.OverIndicatorRule;

import static pab.ta.handler.base.lib.asset.Direction.SELL;

public class MfiSellSignalProducer extends SignalProducer {

    public MfiSellSignalProducer() {
        super("MFI > 80", SELL);
    }

    @Override
    protected Rule getRule(BarSeries series) {
        var indicator = new MoneyFlowIndexIndicator(series, 14);

        return new OverIndicatorRule(indicator, 80);
    }
}
