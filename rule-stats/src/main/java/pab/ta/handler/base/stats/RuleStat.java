package pab.ta.handler.base.stats;

import lombok.Getter;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import pab.ta.handler.base.lib.asset.Direction;

@Getter
public class RuleStat {
    private final Integer next;
    private final Direction direction;
    private final BarSeries series;

    private Integer bullCount = 0;
    private Integer bearCount = 0;

    public RuleStat(BarSeries series, Direction direction, Integer next) {
        this.series = series;
        this.direction = direction;
        this.next = next;
    }


    public RuleStat(BaseRule rule, Integer next) {
        this.series = rule.getBarSeries();
        this.direction = rule.getDirection();
        this.next = next;
    }


    public void calc(Integer signalIndex) {
        if (signalIndex + next > series.getEndIndex()) {
            return;
        }

        var isBullish = isBullish(series.getBar(signalIndex), series.getBar(signalIndex + next));

        if (direction == Direction.BUY && isBullish) {
            bullCount++;
        }

        if (direction == Direction.SELL && !isBullish) {
            bearCount++;
        }
    }

    private boolean isBullish(Bar bar, Bar nextBar) {
        return bar.getClosePrice().longValue() < nextBar.getHighPrice().longValue();
    }
}
