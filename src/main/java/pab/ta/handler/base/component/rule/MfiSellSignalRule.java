package pab.ta.handler.base.component.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.volume.MoneyFlowIndexIndicator;
import org.ta4j.core.rules.OverIndicatorRule;
import pab.ta.handler.base.asset.SeriesContainer;

import static pab.ta.handler.base.asset.Direction.SELL;
import static pab.ta.handler.base.component.rule.IndicatorGroup.MFI;

public class MfiSellSignalRule extends SignalRule {

    public MfiSellSignalRule(SeriesContainer seriesContainer) {
        super(seriesContainer, "MFI > 80", MFI, SELL);
    }

    @Override
    protected Rule getRule(BarSeries series) {
        var indicator = new MoneyFlowIndexIndicator(series, 14);

        return new OverIndicatorRule(indicator, 80);
    }
}
