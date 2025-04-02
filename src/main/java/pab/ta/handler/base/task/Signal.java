package pab.ta.handler.base.task;

import pab.ta.handler.base.asset.AssetType;
import pab.ta.handler.base.asset.CandleInterval;
import pab.ta.handler.base.asset.Direction;
import pab.ta.handler.base.component.rule.IndicatorGroup;

import java.time.LocalDateTime;

public interface Signal {

    String getTicker();

    CandleInterval getInterval();

    IndicatorGroup getIndicatorGroup();

    String getIndicatorName();

    Direction getDirection();

    AssetType getType();

    LocalDateTime getCreatedAt();
}