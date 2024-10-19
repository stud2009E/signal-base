package pab.ta.handler.base.task;

import pab.ta.handler.base.asset.AssetType;
import pab.ta.handler.base.asset.CandleInterval;
import pab.ta.handler.base.asset.Direction;
import pab.ta.handler.base.component.rule.RuleGroup;

import java.time.LocalDateTime;

public interface Signal {

    String getTicker();

    CandleInterval getInterval();

    String getRuleId();

    Direction getDirection();

    AssetType getType();

    RuleGroup getRuleGroup();

    LocalDateTime getCreatedAt();
}