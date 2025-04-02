package pab.ta.handler.base.component.rule;

import org.springframework.stereotype.Component;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CCIIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.OverIndicatorRule;
import org.ta4j.core.rules.UnderIndicatorRule;
import pab.ta.handler.base.asset.BaseRuleIdentity;
import pab.ta.handler.base.asset.Direction;
import pab.ta.handler.base.asset.RuleIdentity;

import java.util.List;

import static pab.ta.handler.base.component.rule.IndicatorGroup.CCI;

@Component
public class CciRuleWrapper extends RuleWrapper {

    @Override
    protected Indicator<Num> indicator() {
        BarSeries series = container.getSeries();
        return new CCIIndicator(series, 20);
    }

    @Override
    public List<RuleIdentity> rules() {
        Indicator<Num> indicator = indicator();

        return List.of(
                new BaseRuleIdentity(
                        "CCI > 100", CCI, new OverIndicatorRule(indicator, 100), Direction.SELL),
                new BaseRuleIdentity(
                        "CCI < -100", CCI, new UnderIndicatorRule(indicator, -100), Direction.BUY)
        );
    }
}
