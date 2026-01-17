package pab.ta.handler.base.lib.asset;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TimeFrame {
    @EqualsAndHashCode.Include
    private final CandleInterval interval;
    @EqualsAndHashCode.Include
    private final ZonedDateTime from;
    @EqualsAndHashCode.Include
    private final ZonedDateTime to;
}
