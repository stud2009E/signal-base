package pab.ta.handler.base.asset;

import java.time.LocalDateTime;

public record BaseTimeFrame(CandleInterval interval, LocalDateTime from, LocalDateTime to) implements TimeFrame{
}
