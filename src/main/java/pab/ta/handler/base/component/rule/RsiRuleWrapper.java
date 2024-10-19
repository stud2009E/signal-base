package pab.ta.handler.base.component.rule;

import org.springframework.stereotype.Component;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.OverIndicatorRule;
import org.ta4j.core.rules.UnderIndicatorRule;
import pab.ta.handler.base.asset.*;

import java.util.List;

import static pab.ta.handler.base.component.rule.RuleGroup.RSI;

@Component
public class RsiRuleWrapper extends RuleWrapper {

    @Override
    public List<RuleIdentity> rules() {
        Indicator<Num> indicator = indicator();

        return List.of(
                new BaseRuleIdentity(
                        "RSI > 70", RSI, container.identity(), new OverIndicatorRule(indicator, 70), Direction.SELL),
                new BaseRuleIdentity(
                        "RSI < 30", RSI, container.identity(), new UnderIndicatorRule(indicator, 30), Direction.BUY)
        );
    }

    @Override
    protected Indicator<Num> indicator() {
        BarSeries series = container.series();

        return new RSIIndicator(new ClosePriceIndicator(series), 14);
    }
}