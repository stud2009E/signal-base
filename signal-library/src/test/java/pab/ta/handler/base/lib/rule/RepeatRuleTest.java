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
import org.ta4j.core.rules.CrossedUpIndicatorRule;

import java.util.stream.Stream;

class RepeatRuleTest {

    private static Indicator<Num> evaluatedIndicator;

    @BeforeAll
    static void initAll() {
        evaluatedIndicator = new FixedDecimalIndicator(
                new BaseBarSeries(),
                100, 90, 80, 90, 100, 130, 80, 90, 100, 140, 80, 100);
    }

    @Test
    @DisplayName("Test for checking repeat rule args")
    void newRepeatRuleTest() {
        Rule crossUpRule = new CrossedUpIndicatorRule(evaluatedIndicator, 85);

        Assertions.assertThrows(IllegalArgumentException.class, () -> new RepeatRule(crossUpRule, 0, 10));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RepeatRule(crossUpRule, 0, 0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RepeatRule(crossUpRule, 1, 0));

        Assertions.assertDoesNotThrow(() -> new RepeatRule(crossUpRule, 1, 1));
        Assertions.assertDoesNotThrow(() -> new RepeatRule(crossUpRule, 2, 1));
        Assertions.assertDoesNotThrow(() -> new RepeatRule(crossUpRule, 1, 2));
        Assertions.assertDoesNotThrow(() -> new RepeatRule(crossUpRule, 10, 10));

    }


    private static Stream<Arguments> repeat2RuleTest() {
        return Stream.of(
                Arguments.of(0, false, false),
                Arguments.of(1, false, false),
                Arguments.of(2, false, false),
                Arguments.of(3, true, false),
                Arguments.of(4, false, false),
                Arguments.of(5, false, false),
                Arguments.of(6, false, false),
                Arguments.of(7, true, true),
                Arguments.of(8, false, false),
                Arguments.of(9, false, false),
                Arguments.of(10, false, false),
                Arguments.of(11, true, true)
        );
    }

    @DisplayName("Test for repeat rule: 2 times")
    @ParameterizedTest
    @MethodSource
    void repeat2RuleTest(int index, boolean crossUpExpected, boolean repeatExpected) {
        Rule crossUpRule = new CrossedUpIndicatorRule(evaluatedIndicator, 85);
        Rule repeatRule = new RepeatRule(crossUpRule, 2, 10);

        Assertions.assertEquals(crossUpExpected, crossUpRule.isSatisfied(index));
        Assertions.assertEquals(repeatExpected, repeatRule.isSatisfied(index));
    }

    private static Stream<Arguments> repeat3RuleTest() {
        return Stream.of(
                Arguments.of(0, false, false),
                Arguments.of(1, false, false),
                Arguments.of(2, false, false),
                Arguments.of(3, true, false),
                Arguments.of(4, false, false),
                Arguments.of(5, false, false),
                Arguments.of(6, false, false),
                Arguments.of(7, true, false),
                Arguments.of(8, false, false),
                Arguments.of(9, false, false),
                Arguments.of(10, false, false),
                Arguments.of(11, true, true)
        );
    }

    @DisplayName("Test for repeat rule: 3 times")
    @ParameterizedTest
    @MethodSource
    void repeat3RuleTest(int index, boolean crossUpExpected, boolean repeatExpected) {
        Rule crossUpRule = new CrossedUpIndicatorRule(evaluatedIndicator, 85);
        Rule repeatRule = new RepeatRule(crossUpRule, 3, 10);

        Assertions.assertEquals(repeatExpected, repeatRule.isSatisfied(index));
        Assertions.assertEquals(crossUpExpected, crossUpRule.isSatisfied(index));
    }

    @AfterAll
    static void tearDownAll() {
        evaluatedIndicator = null;
    }
}