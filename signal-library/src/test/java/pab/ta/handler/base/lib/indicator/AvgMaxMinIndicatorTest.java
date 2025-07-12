package pab.ta.handler.base.lib.indicator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.provider.Arguments;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

import java.util.stream.Stream;

class AvgMaxMinIndicatorTest {

    private static Indicator<Num> indicator1;
    private static Indicator<Num> indicator2;

    @BeforeAll
    static void init() {

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


}
