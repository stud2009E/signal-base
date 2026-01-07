package pab.ta.handler.base.lib.task;

import pab.ta.handler.base.lib.asset.CandleInterval;
import pab.ta.handler.base.lib.exception.RootSignalException;

import java.time.ZonedDateTime;

public class CandleHelperImpl implements CandleHelper {

    @Override
    public ZonedDateTime calculate(ZonedDateTime to, CandleInterval interval) {
        return switch (interval) {
            case H1 -> to.minusWeeks(2);
            case H2 -> to.minusWeeks(4);
            case H4 -> to.minusWeeks(8);
            case DAY -> to.minusWeeks(12);
            case null -> throw new RootSignalException("Undefined candle interval");
        };
    }
}
