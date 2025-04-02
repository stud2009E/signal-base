package pab.ta.handler.base.component.task;

import lombok.*;
import pab.ta.handler.base.asset.AssetType;
import pab.ta.handler.base.asset.CandleInterval;
import pab.ta.handler.base.asset.Direction;
import pab.ta.handler.base.component.rule.IndicatorGroup;
import pab.ta.handler.base.task.Signal;

import java.time.LocalDateTime;

@Getter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class BaseSignal implements Signal {
    @EqualsAndHashCode.Include
    @ToString.Include
    private String ticker;

    private AssetType type;

    @EqualsAndHashCode.Include
    @ToString.Include
    private CandleInterval interval;

    @ToString.Include
    @EqualsAndHashCode.Include
    private String indicatorName;

    @ToString.Include
    @EqualsAndHashCode.Include
    private Direction direction;

    private IndicatorGroup indicatorGroup;

    private LocalDateTime createdAt;
}