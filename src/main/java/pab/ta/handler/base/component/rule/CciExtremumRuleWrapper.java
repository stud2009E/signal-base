package pab.ta.handler.base.component.rule;

import org.springframework.stereotype.Component;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CCIIndicator;
import org.ta4j.core.num.DecimalNum;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import org.ta4j.core.rules.OverIndicatorRule;
import org.ta4j.core.rules.UnderIndicatorRule;
import pab.ta.handler.base.asset.BaseRuleIdentity;
import pab.ta.handler.base.asset.Direction;
import pab.ta.handler.base.asset.RuleIdentity;
import pab.ta.handler.base.indicator.AvgMaxIndicator;
import pab.ta.handler.base.indicator.AvgMinIndicator;

import java.util.List;

import static pab.ta.handler.base.component.rule.RuleGroup.CCI_EXTREMUM;

@Component
public class CciExtremumRuleWrapper extends RuleWrapper {

    @Override
    public List<RuleIdentity> rules() {
        Indicator<Num> indicator = indicator();

        Indicator<Num> avgMax = new AvgMaxIndicator(indicator, DecimalNum.valueOf(70));
        Indicator<Num> avgMin = new AvgMinIndicator(indicator, DecimalNum.valueOf(30));


        return List.of(
                new BaseRuleIdentity(
                        "CCI avgMax(100) up", CCI_EXTREMUM, container.identity(), new OverIndicatorRule(indicator, avgMax), Direction.SELL),
                new BaseRuleIdentity(
                        "CCI avgMin(-100) down", CCI_EXTREMUM, container.identity(), new UnderIndicatorRule(indicator, avgMin), Direction.BUY)
        );
    }

    @Override
    protected Indicator<Num> indicator() {
        BarSeries series = container.series();

        return new CCIIndicator(series, 20);
    }
}