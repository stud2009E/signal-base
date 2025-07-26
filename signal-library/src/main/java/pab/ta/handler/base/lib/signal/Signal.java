package pab.ta.handler.base.lib.signal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import pab.ta.handler.base.lib.asset.CandleInterval;
import pab.ta.handler.base.lib.asset.Direction;
import pab.ta.handler.base.lib.indicator.IndicatorType;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Signal {
    @EqualsAndHashCode.Include
    private String name;

    @EqualsAndHashCode.Include
    private String ticker;

    @EqualsAndHashCode.Include
    private CandleInterval interval;

    private Set<IndicatorType> types = new HashSet<>();

    private Direction direction;

    private ZonedDateTime createdAt;


    public Signal(String name, String ticker, CandleInterval interval, Direction direction, ZonedDateTime createdAt, IndicatorType... types) {
        this.name = name;
        this.ticker = ticker;
        this.interval = interval;
        this.direction = direction;
        this.createdAt = createdAt;
        this.types.addAll(List.of(types));
    }

    public Signal addType(Collection<IndicatorType> types) {
        this.types.addAll(types);

        return this;
    }

    public Signal addType(IndicatorType... types) {
        this.types.addAll(List.of(types));

        return this;
    }
}
