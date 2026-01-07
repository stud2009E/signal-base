package pab.ta.handler.base.stats.counter;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;


public class BearPredicate extends BarPredicate {

    public BearPredicate(BarSeries series, Integer signalIndex) {
        super(series, signalIndex);
    }

    @Override
    protected boolean compareBars(Bar signalBar, Bar offsetBar) {
        return signalBar.getClosePrice().longValue() > offsetBar.getLowPrice().longValue();
    }
}
