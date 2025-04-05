package pab.ta.handler.base.lib.task;

import pab.ta.handler.base.lib.asset.AssetType;
import pab.ta.handler.base.lib.asset.CandleInterval;
import pab.ta.handler.base.lib.asset.Direction;

import java.time.LocalDateTime;

public interface Signal {

    String getTicker();

    CandleInterval getInterval();

    String getIndicatorId();

    Direction getDirection();

    AssetType getType();

    LocalDateTime getCreatedAt();
}