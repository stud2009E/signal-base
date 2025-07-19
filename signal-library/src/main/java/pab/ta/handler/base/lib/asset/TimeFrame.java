package pab.ta.handler.base.lib.asset;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Getter
public class TimeFrame {
    private final CandleInterval interval;

    private final ZonedDateTime from;

    private final ZonedDateTime to;
}
