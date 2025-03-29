package pab.ta.handler.base.asset;

import java.time.LocalDateTime;

public interface TimeFrame {
    CandleInterval getInterval();

    LocalDateTime getFrom();

    LocalDateTime getTo();
}
