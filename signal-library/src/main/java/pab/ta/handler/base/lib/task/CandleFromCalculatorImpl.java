package pab.ta.handler.base.lib.task;

import pab.ta.handler.base.lib.asset.CandleInterval;
import pab.ta.handler.base.lib.exception.RootSignalException;

import java.time.ZonedDateTime;

public class CandleFromCalculatorImpl implements CandleFromCalculator {

    @Override
    public ZonedDateTime calculate(ZonedDateTime to, CandleInterval interval) {
        return switch (interval) {
            case H1 -> to.minusWeeks(1);
            case H2 -> to.minusDays(2);
            case H4 -> to.minusWeeks(4);
            case DAY -> to.minusWeeks(8);
            case null -> throw new RootSignalException("Undefined candle interval");
        };
    }
}
