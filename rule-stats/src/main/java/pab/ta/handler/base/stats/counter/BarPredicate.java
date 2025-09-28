package pab.ta.handler.base.stats.counter;

import lombok.Getter;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;

import java.util.function.Predicate;

@Getter
public abstract class BarPredicate implements Predicate<Integer> {

    private final BarSeries series;
    private final Integer signalIndex;

    public BarPredicate(BarSeries series, Integer signalIndex) {
        this.series = series;
        this.signalIndex = signalIndex;
    }

    @Override
    public boolean test(Integer testIndex) {
        try {
            var signalBar = series.getBar(signalIndex);
            var testBar = series.getBar(signalIndex + testIndex);

            return compareBars(signalBar, testBar);
        } catch (IndexOutOfBoundsException ex) {
            return false;
        }
    }

    protected abstract boolean compareBars(Bar signalBar, Bar testBar);
}
