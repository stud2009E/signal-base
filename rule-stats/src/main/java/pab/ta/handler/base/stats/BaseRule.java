package pab.ta.handler.base.stats;

import lombok.Getter;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import pab.ta.handler.base.lib.asset.Direction;

@Getter
public abstract class BaseRule implements Rule{

    private final String name;
    private final Direction direction;
    private final BarSeries barSeries;

    public BaseRule(String name, Direction direction, BarSeries barSeries) {
        this.name = name;
        this.direction = direction;
        this.barSeries = barSeries;
    }

}
