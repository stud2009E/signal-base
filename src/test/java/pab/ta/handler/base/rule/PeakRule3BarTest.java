package pab.ta.handler.base.rule;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.helpers.FixedDecimalIndicator;
import org.ta4j.core.num.Num;

import java.util.stream.Stream;

class PeakRule3BarTest {

    private static Rule rule;

    @BeforeAll
    static void init() {
        Indicator<Num> indicator = new FixedDecimalIndicator(new BaseBarSeries(),
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

        rule = new PeakRule3Bar(indicator);
    }

    @AfterAll
    static void tearDown() {
        rule = null;
    }


    private static Stream<Arguments> peak3() {
        return Stream.of(
                Arguments.of(0, false),
                Arguments.of(1, false),
                Arguments.of(2, false),
                Arguments.of(3, false),
                Arguments.of(4, false),
                Arguments.of(5, false),
                Arguments.of(6, true),
                Arguments.of(7, false),
                Arguments.of(8, false),
                Arguments.of(9, false),
                Arguments.of(10, true),
                Arguments.of(11, false)
        );
    }


    @ParameterizedTest
    @DisplayName("Test for peak rule with 3 candles")
    @MethodSource
    void peak3(int index, boolean expected) {
        Assertions.assertEquals(expected, rule.isSatisfied(index));
    }
}
