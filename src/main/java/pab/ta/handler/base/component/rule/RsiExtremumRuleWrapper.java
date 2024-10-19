package pab.ta.handler.base.component.rule;

import org.springframework.stereotype.Component;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.DecimalNum;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import pab.ta.handler.base.asset.BaseRuleIdentity;
import pab.ta.handler.base.asset.Direction;
import pab.ta.handler.base.asset.RuleIdentity;
import pab.ta.handler.base.indicator.AvgMaxIndicator;
import pab.ta.handler.base.indicator.AvgMinIndicator;

import java.util.List;

import static pab.ta.handler.base.component.rule.RuleGroup.RSI_EXTREMUM;

@Component
public class RsiExtremumRuleWrapper extends RuleWrapper {

    @Override
    public List<RuleIdentity> rules() {
        Indicator<Num> indicator = indicator();

        Indicator<Num> avgMax = new AvgMaxIndicator(indicator, DecimalNum.valueOf(70));
        Indicator<Num> avgMin = new AvgMinIndicator(indicator, DecimalNum.valueOf(30));


        return List.of(
                new BaseRuleIdentity(
                        "RSI avgMax(70) up", RSI_EXTREMUM, container.identity(), new CrossedUpIndicatorRule(indicator, avgMax), Direction.SELL),
                new BaseRuleIdentity(
                        "RSI avgMin(30) down", RSI_EXTREMUM, container.identity(), new CrossedDownIndicatorRule(indicator, avgMin), Direction.BUY)
        );
    }

    @Override
    protected Indicator<Num> indicator() {
        BarSeries series = container.series();

        return new RSIIndicator(new ClosePriceIndicator(series), 14);
    }
}