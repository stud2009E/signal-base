package pab.ta.handler.base.lib.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.AbstractIndicator;
import org.ta4j.core.num.Num;

import java.util.ArrayList;
import java.util.Comparator;
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

        return values.getFirst();
    }

    @Override
    public int getUnstableBars() {
        return this.barCount;
    }
}
