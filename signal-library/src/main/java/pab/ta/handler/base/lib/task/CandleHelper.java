package pab.ta.handler.base.lib.task;

import pab.ta.handler.base.lib.asset.CandleInterval;

import java.time.ZonedDateTime;

public interface CandleHelper {

    ZonedDateTime calculate(ZonedDateTime to, CandleInterval interval);

}
