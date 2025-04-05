package pab.ta.handler.base.lib.asset;

import java.time.LocalDateTime;

public interface TimeFrame {
    CandleInterval getInterval();

    LocalDateTime getFrom();

    LocalDateTime getTo();
}
