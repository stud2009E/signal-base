package pab.ta.handler.base.lib.indicator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.helpers.FixedDecimalIndicator;
import org.ta4j.core.num.DecimalNum;
import org.ta4j.core.num.Num;

import java.util.stream.Stream;

class AvgMaxMinIndicatorTest {

    private static Indicator<Num> indicator1;
    private static Indicator<Num> indicator2;

    @BeforeAll
    static void init() {

        indicator1 = new FixedDecimalIndicator(new BaseBarSeries(),
                100, //0
                90, //1
                80, //2
                90, //3
                100, //4
                130, //5
                80, //6
                90, //7
                100, //8
                150, //9
                80, //10
                100  //11
        );

        indicator2 = new FixedDecimalIndicator(new BaseBarSeries(),
                34, //0
                21, //1
                20, //2
                29, //3
                35, //4
                50, //5
                40, //6
                45, //7
                31, //8
                22, //9
                10, //10
                40  //11
        );
    }

    @AfterAll
    static void tearDown() {
        indicator1 = null;
        indicator2 = null;
    }

    private static Stream<Arguments> avgMax110() {
        return Stream.of(
                Arguments.of(0, 110),
                Arguments.of(1, 110),
                Arguments.of(2, 110),
                Arguments.of(3, 110),
                Arguments.of(4, 110),
                Arguments.of(5, 110),
                Arguments.of(6, 128),
                Arguments.of(7, 128),
                Arguments.of(8, 128),
                Arguments.of(9, 128),
                Arguments.of(10, 140),
                Arguments.of(11, 140)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("avg max test")
    void avgMax110(int index, int value) {
        Indicator<Num> avgMax = new AvgMaxIndicator(indicator1, DecimalNum.valueOf(110));

        Assertions.assertEquals(avgMax.getValue(index), DecimalNum.valueOf(value));
    }

    private static Stream<Arguments> avgMin30() {
        return Stream.of(
                Arguments.of(0, 30),
                Arguments.of(1, 30),
                Arguments.of(2, 30),
                Arguments.of(3, 21),
                Arguments.of(4, 21),
                Arguments.of(5, 21),
                Arguments.of(6, 21),
                Arguments.of(7, 21),
                Arguments.of(8, 21),
                Arguments.of(9, 21),
                Arguments.of(10, 21),
                Arguments.of(11, 15)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("avg min test")
    void avgMin30(int index, int value) {
        Indicator<Num> avgMin = new AvgMinIndicator(indicator2, DecimalNum.valueOf(30));

        Assertions.assertEquals(avgMin.getValue(index), DecimalNum.valueOf(value));
    }

}
