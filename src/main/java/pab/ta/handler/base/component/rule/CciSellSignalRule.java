package pab.ta.handler.base.component.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.CCIIndicator;
import org.ta4j.core.rules.OverIndicatorRule;
import pab.ta.handler.base.asset.SeriesContainer;

import static pab.ta.handler.base.asset.Direction.SELL;
import static pab.ta.handler.base.component.rule.IndicatorGroup.CCI;

public class CciSellSignalRule extends SignalRule {

    public CciSellSignalRule(SeriesContainer seriesContainer) {
        super(seriesContainer, "CCI > 100", CCI, SELL);

    }

    @Override
    protected Rule getRule(BarSeries series) {
        var indicator = new CCIIndicator(series, 14);

        return new OverIndicatorRule(indicator, 100);
    }
}
