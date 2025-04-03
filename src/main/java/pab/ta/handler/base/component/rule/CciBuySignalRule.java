package pab.ta.handler.base.component.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.CCIIndicator;
import org.ta4j.core.rules.UnderIndicatorRule;

import static pab.ta.handler.base.asset.Direction.BUY;
import static pab.ta.handler.base.component.rule.IndicatorGroup.CCI;

public class CciBuySignalRule extends SignalRule {

    public CciBuySignalRule() {
        super("CCI < -100", CCI, BUY);
    }

    @Override
    protected Rule getRule(BarSeries series) {
        var indicator = new CCIIndicator(series, 14);

        return new UnderIndicatorRule(indicator, -100);
    }
}
