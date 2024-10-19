package pab.ta.handler.base.asset;

import java.time.LocalDateTime;

public interface TimeFrame {
    CandleInterval interval();

    LocalDateTime from();

    LocalDateTime to();
}
