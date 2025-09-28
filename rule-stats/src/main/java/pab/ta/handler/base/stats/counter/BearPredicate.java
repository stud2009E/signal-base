package pab.ta.handler.base.stats.counter;

import lombok.Getter;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;

@Getter
public class BearPredicate extends BarPredicate {

    public BearPredicate(BarSeries series, Integer signalIndex) {
        super(series, signalIndex);
    }

    @Override
    protected boolean compareBars(Bar signalBar, Bar testBar) {
        return signalBar.getClosePrice().longValue() > testBar.getLowPrice().longValue();
    }
}
