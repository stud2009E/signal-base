package pab.ta.handler.base.lib.asset;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ta4j.core.BarSeries;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AssetData {
    @EqualsAndHashCode.Include
    private final AssetInfo info;

    @EqualsAndHashCode.Include
    private final TimeFrame timeFrame;

    private final ZonedDateTime createdAt;

    private final BarSeries barSeries;

    public CandleInterval getInterval() {
        return timeFrame.getInterval();
    }

    public String getTicker(){
        return info.getTicker();
    }
}