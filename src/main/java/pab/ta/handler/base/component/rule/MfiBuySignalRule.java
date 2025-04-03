package pab.ta.handler.base.component.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.volume.MoneyFlowIndexIndicator;
import org.ta4j.core.rules.UnderIndicatorRule;
import pab.ta.handler.base.asset.SeriesContainer;

import static pab.ta.handler.base.asset.Direction.BUY;
import static pab.ta.handler.base.component.rule.IndicatorGroup.MFI;

public class MfiBuySignalRule extends SignalRule {

    public MfiBuySignalRule(SeriesContainer seriesContainer) {
        super(seriesContainer, "MFI < 20", MFI, BUY);
    }

    @Override
    protected Rule getRule(BarSeries series) {
        var indicator = new MoneyFlowIndexIndicator(series, 14);

        return new UnderIndicatorRule(indicator, 20);
    }
}
