package pab.ta.handler.base.lib.task;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import pab.ta.handler.base.lib.asset.AssetType;
import pab.ta.handler.base.lib.asset.CandleInterval;
import pab.ta.handler.base.lib.asset.Direction;

import java.time.LocalDateTime;

@Getter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class BaseSignal implements Signal {
    @EqualsAndHashCode.Include
    @ToString.Include
    private String ticker;

    @EqualsAndHashCode.Include
    @ToString.Include
    private CandleInterval interval;

    @ToString.Include
    @EqualsAndHashCode.Include
    private Direction direction;

    @ToString.Include
    @EqualsAndHashCode.Include
    private String indicatorId;

    private AssetType type;
    private LocalDateTime createdAt;
}