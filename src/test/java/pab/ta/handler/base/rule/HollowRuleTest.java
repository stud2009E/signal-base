package pab.ta.handler.base.rule;

import org.junit.jupiter.api.AfterEach;
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
import pab.ta.handler.base.rule.HollowRule3Bar;

@ExtendWith(MockitoExtension.class)
public class HollowRuleTest {

    private BarSeries series;

    @BeforeEach
    public void setUp() {
        series = new BaseBarSeries();
    }

    @AfterEach
    public void cleanUp() {
        series = null;
    }

    @Test
    public void hollow3TestSeries3BarPositive() {
        Indicator<Num> indicator = new FixedDecimalIndicator(series,
                50, //0
                25, //1
                40 //2
        );

        Rule rule = new HollowRule3Bar(indicator);
        Assertions.assertFalse(rule.isSatisfied(0));
        Assertions.assertFalse(rule.isSatisfied(1));
        Assertions.assertTrue(rule.isSatisfied(2));
    }

    @Test
    public void hollow3Test() {
        Indicator<Num> indicator = new FixedDecimalIndicator(series,
                100, //0
                90, //1
                80, //2
                90, //3
                100, //4
                130, //5
                80, //6
                90, //7
                100, //8
                140, //9
                80, //10
                100  //11
        );

        Rule rule = new HollowRule3Bar(indicator);
        Assertions.assertFalse(rule.isSatisfied(0));
        Assertions.assertFalse(rule.isSatisfied(1));
        Assertions.assertFalse(rule.isSatisfied(2));
        Assertions.assertTrue(rule.isSatisfied(3));
        Assertions.assertFalse(rule.isSatisfied(4));
        Assertions.assertFalse(rule.isSatisfied(5));
        Assertions.assertFalse(rule.isSatisfied(6));
        Assertions.assertTrue(rule.isSatisfied(7));
        Assertions.assertFalse(rule.isSatisfied(8));
        Assertions.assertFalse(rule.isSatisfied(9));
        Assertions.assertFalse(rule.isSatisfied(10));
        Assertions.assertTrue(rule.isSatisfied(11));
    }
}
