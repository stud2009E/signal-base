package pab.ta.handler.base.component.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.rules.OverIndicatorRule;
import pab.ta.handler.base.asset.SeriesContainer;

import static pab.ta.handler.base.asset.Direction.SELL;
import static pab.ta.handler.base.component.rule.IndicatorGroup.RSI;

public class RsiSellSignalRule extends SignalRule {

    public RsiSellSignalRule(SeriesContainer seriesContainer) {
        super(seriesContainer, "RSI > 70", RSI, SELL);
    }

    @Override
    protected Rule getRule(BarSeries series) {
        var indicator = new RSIIndicator(new ClosePriceIndicator(series), 14);

        return new OverIndicatorRule(indicator, 70);
    }
}
