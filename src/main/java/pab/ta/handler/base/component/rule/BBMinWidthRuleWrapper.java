package pab.ta.handler.base.component.rule;

import org.springframework.stereotype.Component;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.UnderIndicatorRule;
import pab.ta.handler.base.asset.BaseRuleIdentity;
import pab.ta.handler.base.asset.Direction;
import pab.ta.handler.base.asset.RuleIdentity;
import pab.ta.handler.base.indicator.BBWidth;
import pab.ta.handler.base.indicator.BBWidthMin;

import java.util.List;

import static pab.ta.handler.base.component.rule.RuleGroup.VOLATILITY;

@Component
public class BBMinWidthRuleWrapper extends RuleWrapper {

    @Override
    public List<RuleIdentity> rules() {
        Indicator<Num> bbWidth = indicator();
        Indicator<Num> bbWidthMin = new BBWidthMin(container.getSeries(), 14);

        return List.of(
                new BaseRuleIdentity(
                        "BB width volatility", VOLATILITY, new UnderIndicatorRule(bbWidth, bbWidthMin), Direction.SELL)
        );
    }

    @Override
    protected Indicator<Num> indicator() {
        BarSeries series = container.getSeries();

        return new BBWidth(series, 14);
    }
}