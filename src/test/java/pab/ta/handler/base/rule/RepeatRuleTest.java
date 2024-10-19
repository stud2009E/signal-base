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
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import pab.ta.handler.base.rule.RepeatRule;

@ExtendWith(MockitoExtension.class)
public class RepeatRuleTest {

    private BarSeries series;

    @BeforeEach
    public void setUp(){
        series = new BaseBarSeries();
    }

    @Test
    public void newRepeatRuleTest(){
        Indicator<Num> evaluatedIndicator = new FixedDecimalIndicator(series, 100, 90, 80, 90, 100, 130, 80, 90, 100, 140, 80, 100);
        Rule crossUpRule = new CrossedUpIndicatorRule(evaluatedIndicator, 85);

        Assertions.assertThrows(IllegalArgumentException.class, () -> new RepeatRule(crossUpRule, 0, 10));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RepeatRule(crossUpRule, 0, 0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RepeatRule(crossUpRule, 1, 0));

        Assertions.assertDoesNotThrow(() -> new RepeatRule(crossUpRule, 1, 1));
        Assertions.assertDoesNotThrow(() -> new RepeatRule(crossUpRule, 2, 1));
        Assertions.assertDoesNotThrow(() -> new RepeatRule(crossUpRule, 1, 2));
        Assertions.assertDoesNotThrow(() -> new RepeatRule(crossUpRule, 10, 10));

    }

    @Test
    public void repeat2RuleTest() {
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

        Rule repeatRule = new RepeatRule(crossUpRule, 2, 10);
        Assertions.assertFalse(repeatRule.isSatisfied(0));
        Assertions.assertFalse(repeatRule.isSatisfied(1));
        Assertions.assertFalse(repeatRule.isSatisfied(2));
        Assertions.assertFalse(repeatRule.isSatisfied(3));
        Assertions.assertFalse(repeatRule.isSatisfied(4));
        Assertions.assertFalse(repeatRule.isSatisfied(5));
        Assertions.assertFalse(repeatRule.isSatisfied(6));
        Assertions.assertTrue(repeatRule.isSatisfied(7));
        Assertions.assertFalse(repeatRule.isSatisfied(8));
        Assertions.assertFalse(repeatRule.isSatisfied(9));
        Assertions.assertFalse(repeatRule.isSatisfied(10));
        Assertions.assertTrue(repeatRule.isSatisfied(11));
    }

    @Test
    public void repeat3RuleTest() {
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

        Rule repeatRule = new RepeatRule(crossUpRule, 3, 10);
        Assertions.assertFalse(repeatRule.isSatisfied(0));
        Assertions.assertFalse(repeatRule.isSatisfied(1));
        Assertions.assertFalse(repeatRule.isSatisfied(2));
        Assertions.assertFalse(repeatRule.isSatisfied(3));
        Assertions.assertFalse(repeatRule.isSatisfied(4));
        Assertions.assertFalse(repeatRule.isSatisfied(5));
        Assertions.assertFalse(repeatRule.isSatisfied(6));
        Assertions.assertFalse(repeatRule.isSatisfied(7));
        Assertions.assertFalse(repeatRule.isSatisfied(8));
        Assertions.assertFalse(repeatRule.isSatisfied(9));
        Assertions.assertFalse(repeatRule.isSatisfied(10));
        Assertions.assertTrue(repeatRule.isSatisfied(11));
    }
}