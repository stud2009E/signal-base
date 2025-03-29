package pab.ta.handler.base.component.rule;

import org.springframework.stereotype.Component;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CCIIndicator;
import org.ta4j.core.num.DecimalNum;
import org.ta4j.core.num.Num;
import pab.ta.handler.base.asset.BaseRuleIdentity;
import pab.ta.handler.base.asset.Direction;
import pab.ta.handler.base.asset.RuleIdentity;
import pab.ta.handler.base.rule.DivergenceSellRule;

import java.util.List;

import static pab.ta.handler.base.component.rule.RuleGroup.DVG;

@Component
public class DivergenceRuleWrapper extends RuleWrapper {
    @Override
    protected Indicator<Num> indicator() {
        BarSeries series = container.getSeries();
        return new CCIIndicator(series, 14);
    }

    @Override
    public List<RuleIdentity> rules() {


        return List.of(
                new BaseRuleIdentity(
                        "diver sell", DVG, new DivergenceSellRule(indicator(), DecimalNum.valueOf(100)), Direction.SELL)
        );
    }
}
