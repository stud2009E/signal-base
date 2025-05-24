package pab.ta.handler.base.lib.asset;

import java.time.ZonedDateTime;

public interface TimeFrame {
    CandleInterval getInterval();

    ZonedDateTime getFrom();

    ZonedDateTime getTo();
}
