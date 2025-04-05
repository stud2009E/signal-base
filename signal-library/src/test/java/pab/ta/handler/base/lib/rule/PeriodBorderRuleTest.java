package pab.ta.handler.base.lib.rule;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.helpers.FixedDecimalIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;

import java.util.List;
import java.util.stream.Stream;

class PeriodBorderRuleTest {

    private static Indicator<Num> evaluatedIndicator;
    private static Rule crossUp85Rule;
    private static Rule crossDown125Rule;

    @BeforeAll
    static void initAll() {
        evaluatedIndicator = new FixedDecimalIndicator(new BaseBarSeries(), 100, 90, 80, 90, 100, 130, 80, 90, 100, 140, 80, 100);
        crossUp85Rule = new CrossedUpIndicatorRule(evaluatedIndicator, 85);
        crossDown125Rule = new CrossedDownIndicatorRule(evaluatedIndicator, 125);
    }

    @AfterAll
    static void tearDown() {
        evaluatedIndicator = null;
        crossUp85Rule = null;
        crossDown125Rule = null;
    }


    @Test
    void newPeriodBorderRuleTest() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new PeriodBorderRule(List.of(crossUp85Rule, crossUp85Rule), 0));
        Assertions.assertDoesNotThrow(() -> new PeriodBorderRule(crossUp85Rule, 1));
        Assertions.assertDoesNotThrow(() -> new PeriodBorderRule(List.of(crossUp85Rule), 1));
    }

    private static Stream<Arguments> periodBorder3Rule() {
        return Stream.of(
                Arguments.of(0, false),
                Arguments.of(1, false),
                Arguments.of(2, false),
                Arguments.of(3, true),
                Arguments.of(4, true),
                Arguments.of(5, true),
                Arguments.of(6, false),
                Arguments.of(7, true),
                Arguments.of(8, true),
                Arguments.of(9, true),
                Arguments.of(10, false),
                Arguments.of(11, true)
        );
    }

    @DisplayName("Test for border 3 period")
    @ParameterizedTest
    @MethodSource
    void periodBorder3Rule(int index, boolean expected) {
        Rule rule = new PeriodBorderRule(crossUp85Rule, 3);

        Assertions.assertEquals(expected, rule.isSatisfied(index));
    }

    private static Stream<Arguments> periodBorder3RuleList() {
        return Stream.of(
                Arguments.of(0, false),
                Arguments.of(1, false),
                Arguments.of(2, false),
                Arguments.of(3, false),
                Arguments.of(4, false),
                Arguments.of(5, false),
                Arguments.of(6, false),
                Arguments.of(7, true),
                Arguments.of(8, true),
                Arguments.of(9, false),
                Arguments.of(10, false),
                Arguments.of(11, true)
        );
    }

    @DisplayName("Test for rule list border 3 period")
    @ParameterizedTest
    @MethodSource
    void periodBorder3RuleList(int index, boolean expected) {
        Rule rule = new PeriodBorderRule(List.of(crossUp85Rule, crossDown125Rule), 3);

        Assertions.assertEquals(expected, rule.isSatisfied(index));
    }

}