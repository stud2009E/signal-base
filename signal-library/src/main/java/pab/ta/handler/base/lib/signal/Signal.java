package pab.ta.handler.base.lib.signal;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ta4j.core.Rule;
import pab.ta.handler.base.lib.asset.CandleInterval;
import pab.ta.handler.base.lib.asset.Direction;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Signal {
    @EqualsAndHashCode.Include
    private final String name;

    @EqualsAndHashCode.Include
    private final String ticker;

    @EqualsAndHashCode.Include
    private final CandleInterval interval;

    private final Direction direction;

    @Builder.Default
    private final ZonedDateTime createdAt = ZonedDateTime.now();

    private final Rule rule;
}
