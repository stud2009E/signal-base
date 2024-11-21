package pab.ta.handler.base.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.AbstractIndicator;
import org.ta4j.core.num.DecimalNum;
import org.ta4j.core.num.Num;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;

public class BBWidthMin extends AbstractIndicator<Num> {

    private final Indicator<Num> bbw;
    private final int barCount;

    public BBWidthMin(BarSeries series, int barCount) {
        super(series);
        this.barCount = barCount;
        bbw = new BBWidth(series, barCount);
    }

    @Override
    public Num getValue(int index) {
        List<Num> values = new ArrayList<>();

        for (int i = index; i > getUnstableBars(); i--) {
            values.add(bbw.getValue(i));
        }
        values.sort(Comparator.comparingInt(Num::intValue));

        DoubleSummaryStatistics avg = values.stream()
                .limit(10)
                .mapToDouble(Num::doubleValue)
                .summaryStatistics();

        return DecimalNum.valueOf(avg.getAverage());
    }

    @Override
    public int getUnstableBars() {
        return this.barCount;
    }
}
