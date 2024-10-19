package pab.ta.handler.base.rule;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.helpers.FixedDecimalIndicator;
import org.ta4j.core.num.DecimalNum;
import org.ta4j.core.num.Num;
import pab.ta.handler.base.indicator.AvgMaxIndicator;
import pab.ta.handler.base.indicator.AvgMinIndicator;

public class AvgMaxIndicatorTest {

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
    public void avgMax110(){
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
                150, //9
                80, //10
                100  //11
        );

        Indicator<Num> avgMax = new AvgMaxIndicator(indicator, DecimalNum.valueOf(110));

        Assertions.assertEquals(avgMax.getValue(0), DecimalNum.valueOf(110));
        Assertions.assertEquals(avgMax.getValue(1), DecimalNum.valueOf(110));
        Assertions.assertEquals(avgMax.getValue(2), DecimalNum.valueOf(110));
        Assertions.assertEquals(avgMax.getValue(3), DecimalNum.valueOf(110));
        Assertions.assertEquals(avgMax.getValue(4), DecimalNum.valueOf(110));
        Assertions.assertEquals(avgMax.getValue(5), DecimalNum.valueOf(110));
        Assertions.assertEquals(avgMax.getValue(6), DecimalNum.valueOf(120));
        Assertions.assertEquals(avgMax.getValue(7), DecimalNum.valueOf(120));
        Assertions.assertEquals(avgMax.getValue(8), DecimalNum.valueOf(120));
        Assertions.assertEquals(avgMax.getValue(9), DecimalNum.valueOf(120));
        Assertions.assertEquals(avgMax.getValue(10), DecimalNum.valueOf(140));
        Assertions.assertEquals(avgMax.getValue(11), DecimalNum.valueOf(140));
    }

    @Test
    public void avgMin30(){
        Indicator<Num> indicator = new FixedDecimalIndicator(series,
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

        Indicator<Num> avgMin = new AvgMinIndicator(indicator, DecimalNum.valueOf(30));

        Assertions.assertEquals(avgMin.getValue(0), DecimalNum.valueOf(30));
        Assertions.assertEquals(avgMin.getValue(1), DecimalNum.valueOf(30));
        Assertions.assertEquals(avgMin.getValue(2), DecimalNum.valueOf(30));
        Assertions.assertEquals(avgMin.getValue(3), DecimalNum.valueOf(25));
        Assertions.assertEquals(avgMin.getValue(4), DecimalNum.valueOf(25));
        Assertions.assertEquals(avgMin.getValue(5), DecimalNum.valueOf(25));
        Assertions.assertEquals(avgMin.getValue(6), DecimalNum.valueOf(25));
        Assertions.assertEquals(avgMin.getValue(7), DecimalNum.valueOf(25));
        Assertions.assertEquals(avgMin.getValue(8), DecimalNum.valueOf(25));
        Assertions.assertEquals(avgMin.getValue(9), DecimalNum.valueOf(25));
        Assertions.assertEquals(avgMin.getValue(10), DecimalNum.valueOf(25));
        Assertions.assertEquals(avgMin.getValue(11), DecimalNum.valueOf(15));
    }

}
