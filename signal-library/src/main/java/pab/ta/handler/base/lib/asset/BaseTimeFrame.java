package pab.ta.handler.base.lib.asset;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class BaseTimeFrame implements TimeFrame {
    private final CandleInterval interval;
    private final LocalDateTime from;
    private final LocalDateTime to;
}
