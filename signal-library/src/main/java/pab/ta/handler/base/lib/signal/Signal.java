package pab.ta.handler.base.lib.signal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import pab.ta.handler.base.lib.asset.CandleInterval;
import pab.ta.handler.base.lib.asset.Direction;

import java.time.ZonedDateTime;

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

    private Direction direction;

    private ZonedDateTime createdAt;


    public Signal(String name, String ticker, CandleInterval interval, Direction direction, ZonedDateTime createdAt) {
        this.name = name;
        this.ticker = ticker;
        this.interval = interval;
        this.direction = direction;
        this.createdAt = createdAt;
    }

}
