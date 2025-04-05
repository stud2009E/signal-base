package pab.ta.handler.base.task;

import pab.ta.handler.base.asset.AssetType;
import pab.ta.handler.base.asset.CandleInterval;
import pab.ta.handler.base.asset.Direction;

import java.time.LocalDateTime;

public interface Signal {

    String getTicker();

    CandleInterval getInterval();

    String getIndicatorId();

    Direction getDirection();

    AssetType getType();

    LocalDateTime getCreatedAt();
}