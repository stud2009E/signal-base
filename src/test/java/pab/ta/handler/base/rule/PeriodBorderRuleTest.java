package pab.ta.handler.base.rule;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.helpers.FixedDecimalIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import pab.ta.handler.base.rule.PeriodBorderRule;
import pab.ta.handler.base.rule.RepeatRule;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PeriodBorderRuleTest {

    private BarSeries series;

    @BeforeEach
    public void setUp() {
        series = new BaseBarSeries();
    }


    @Test
    public void newPeriodBorderRuleTest() {
        Indicator<Num> evaluatedIndicator = new FixedDecimalIndicator(series, 100, 90, 80, 90, 100, 130, 80, 90, 100, 140, 80, 100);
        Rule crossUpRule = new CrossedUpIndicatorRule(evaluatedIndicator, 85);

        Assertions.assertThrows(IllegalArgumentException.class, () -> new PeriodBorderRule(List.of(crossUpRule, crossUpRule), 0));
        Assertions.assertDoesNotThrow(() -> new PeriodBorderRule(crossUpRule, 1));
        Assertions.assertDoesNotThrow(() -> new PeriodBorderRule(List.of(crossUpRule), 1));
    }

    @Test
    public void periodSingleRuleTest() {
        Indicator<Num> evaluatedIndicator = new FixedDecimalIndicator(series, 100, 90, 80, 90, 100, 130, 80, 90, 100, 140, 80, 100);

        Rule crossUpRule = new CrossedUpIndicatorRule(evaluatedIndicator, 85);
        Assertions.assertFalse(crossUpRule.isSatisfied(0));
        Assertions.assertFalse(crossUpRule.isSatisfied(1));
        Assertions.assertFalse(crossUpRule.isSatisfied(2));
        Assertions.assertTrue(crossUpRule.isSatisfied(3));
        Assertions.assertFalse(crossUpRule.isSatisfied(4));
        Assertions.assertFalse(crossUpRule.isSatisfied(5));
        Assertions.assertFalse(crossUpRule.isSatisfied(6));
        Assertions.assertTrue(crossUpRule.isSatisfied(7));
        Assertions.assertFalse(crossUpRule.isSatisfied(8));
        Assertions.assertFalse(crossUpRule.isSatisfied(9));
        Assertions.assertFalse(crossUpRule.isSatisfied(10));
        Assertions.assertTrue(crossUpRule.isSatisfied(11));

        Rule rule = new PeriodBorderRule(crossUpRule, 3);
        Assertions.assertFalse(rule.isSatisfied(0));
        Assertions.assertFalse(rule.isSatisfied(1));
        Assertions.assertFalse(rule.isSatisfied(2));
        Assertions.assertTrue(rule.isSatisfied(3));
        Assertions.assertTrue(rule.isSatisfied(4));
        Assertions.assertTrue(rule.isSatisfied(5));
        Assertions.assertFalse(rule.isSatisfied(6));
        Assertions.assertTrue(rule.isSatisfied(7));
        Assertions.assertTrue(rule.isSatisfied(8));
        Assertions.assertTrue(rule.isSatisfied(9));
        Assertions.assertFalse(rule.isSatisfied(10));
        Assertions.assertTrue(rule.isSatisfied(11));
    }

    @Test
    public void periodBorderRuleListTest() {
        Indicator<Num> evaluatedIndicator = new FixedDecimalIndicator(series, 100, 90, 80, 90, 100, 130, 80, 90, 100, 140, 80, 100);

        Rule crossUpRule = new CrossedUpIndicatorRule(evaluatedIndicator, 85);
        Assertions.assertFalse(crossUpRule.isSatisfied(0));
        Assertions.assertFalse(crossUpRule.isSatisfied(1));
        Assertions.assertFalse(crossUpRule.isSatisfied(2));
        Assertions.assertTrue(crossUpRule.isSatisfied(3));
        Assertions.assertFalse(crossUpRule.isSatisfied(4));
        Assertions.assertFalse(crossUpRule.isSatisfied(5));
        Assertions.assertFalse(crossUpRule.isSatisfied(6));
        Assertions.assertTrue(crossUpRule.isSatisfied(7));
        Assertions.assertFalse(crossUpRule.isSatisfied(8));
        Assertions.assertFalse(crossUpRule.isSatisfied(9));
        Assertions.assertFalse(crossUpRule.isSatisfied(10));
        Assertions.assertTrue(crossUpRule.isSatisfied(11));

        Rule crossDownRule = new CrossedDownIndicatorRule(evaluatedIndicator, 125);
        Assertions.assertFalse(crossDownRule.isSatisfied(0));
        Assertions.assertFalse(crossDownRule.isSatisfied(1));
        Assertions.assertFalse(crossDownRule.isSatisfied(2));
        Assertions.assertFalse(crossDownRule.isSatisfied(3));
        Assertions.assertFalse(crossDownRule.isSatisfied(4));
        Assertions.assertFalse(crossDownRule.isSatisfied(5));
        Assertions.assertTrue(crossDownRule.isSatisfied(6));
        Assertions.assertFalse(crossDownRule.isSatisfied(7));
        Assertions.assertFalse(crossDownRule.isSatisfied(8));
        Assertions.assertFalse(crossDownRule.isSatisfied(9));
        Assertions.assertTrue(crossDownRule.isSatisfied(10));
        Assertions.assertFalse(crossDownRule.isSatisfied(11));

        Rule rule = new PeriodBorderRule(List.of(crossDownRule, crossUpRule), 3);
        Assertions.assertFalse(rule.isSatisfied(0));
        Assertions.assertFalse(rule.isSatisfied(1));
        Assertions.assertFalse(rule.isSatisfied(2));
        Assertions.assertFalse(rule.isSatisfied(3));
        Assertions.assertFalse(rule.isSatisfied(4));
        Assertions.assertFalse(rule.isSatisfied(5));
        Assertions.assertFalse(rule.isSatisfied(6));
        Assertions.assertTrue(rule.isSatisfied(7));
        Assertions.assertTrue(rule.isSatisfied(8));
        Assertions.assertFalse(rule.isSatisfied(9));
        Assertions.assertFalse(rule.isSatisfied(10));
        Assertions.assertTrue(rule.isSatisfied(11));
    }

}