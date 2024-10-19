package pab.ta.handler.base.component.rule;

import org.springframework.stereotype.Component;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.volume.MoneyFlowIndexIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.OverIndicatorRule;
import org.ta4j.core.rules.UnderIndicatorRule;
import pab.ta.handler.base.asset.*;

import java.util.List;

import static pab.ta.handler.base.component.rule.RuleGroup.MFI;

@Component
public class MfiRuleWrapper extends RuleWrapper {

    @Override
    public List<RuleIdentity> rules() {
        Indicator<Num> indicator = indicator();

        return List.of(
                new BaseRuleIdentity(
                        "MFI > 80", MFI, container.identity(), new OverIndicatorRule(indicator, 80), Direction.SELL),
                new BaseRuleIdentity(
                        "MFI < 20", MFI, container.identity(), new UnderIndicatorRule(indicator, 20), Direction.BUY)
        );
    }

    @Override
    protected Indicator<Num> indicator() {
        BarSeries series = container.series();

        return new MoneyFlowIndexIndicator(series, 14);
    }

    @Override
    public boolean applicableForType(AssetType type) {
        return List.of(AssetType.FUTURE, AssetType.CURRENCY, AssetType.SHARE).contains(type);
    }

    @Override
    public boolean applicableForInterval(CandleInterval interval) {
        return List.of(CandleInterval.DAY, CandleInterval.WEEK).contains(interval);
    }
}