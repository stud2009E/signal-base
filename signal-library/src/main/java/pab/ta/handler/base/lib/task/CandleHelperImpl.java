package pab.ta.handler.base.lib.task;

import pab.ta.handler.base.lib.asset.CandleInterval;
import pab.ta.handler.base.lib.exception.RootSignalException;

import java.time.ZonedDateTime;

public class CandleHelperImpl implements CandleHelper {

    @Override
    public ZonedDateTime calculateFrom(ZonedDateTime to, CandleInterval interval) {
        return switch (interval) {
            case H1 -> to.minusWeeks(1);
            case H2 -> to.minusWeeks(2);
            case H4 -> to.minusWeeks(4);
            case DAY -> to.minusWeeks(16);
            case WEEK -> to.minusWeeks(32);
            case null -> throw new RootSignalException("Undefined candle interval");
        };
    }
}
