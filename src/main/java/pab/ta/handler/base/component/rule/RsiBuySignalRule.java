package pab.ta.handler.base.component.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.rules.OverIndicatorRule;
import pab.ta.handler.base.asset.SeriesContainer;

import static pab.ta.handler.base.asset.Direction.BUY;
import static pab.ta.handler.base.component.rule.IndicatorGroup.RSI;

public class RsiBuySignalRule extends SignalRule {

    public RsiBuySignalRule(SeriesContainer seriesContainer) {
        super(seriesContainer, "RSI < 30", RSI, BUY);
    }

    @Override
    protected Rule getRule(BarSeries series) {
        var indicator = new RSIIndicator(new ClosePriceIndicator(series), 14);

        return new OverIndicatorRule(indicator, 30);
    }
}
