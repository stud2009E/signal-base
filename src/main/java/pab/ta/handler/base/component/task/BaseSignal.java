package pab.ta.handler.base.component.task;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import pab.ta.handler.base.asset.AssetType;
import pab.ta.handler.base.asset.CandleInterval;
import pab.ta.handler.base.asset.Direction;
import pab.ta.handler.base.component.rule.RuleGroup;
import pab.ta.handler.base.task.Signal;

import java.time.LocalDateTime;

@Getter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class BaseSignal implements Signal {
    @EqualsAndHashCode.Include
    @ToString.Include
    String ticker;
    @EqualsAndHashCode.Include
    @ToString.Include
    CandleInterval interval;
    @ToString.Include
    @EqualsAndHashCode.Include
    String ruleId;
    @ToString.Include
    @EqualsAndHashCode.Include
    Direction direction;
    AssetType type;
    @Enumerated(EnumType.STRING)
    RuleGroup ruleGroup;
    LocalDateTime createdAt;
}