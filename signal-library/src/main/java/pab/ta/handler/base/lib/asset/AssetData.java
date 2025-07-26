package pab.ta.handler.base.lib.asset;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;
import pab.ta.handler.base.lib.indicator.IndicatorType;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@Builder
public class AssetData {
    private final AssetInfo info;

    private final CandleInterval interval;

    private final ZonedDateTime createdAt;

    private final Map<IndicatorType, Indicator<Num>> indicatorMap = new HashMap<>();

    public void putIndicator(IndicatorType key, Indicator<Num> value) {
        indicatorMap.put(key, value);
    }

    public Indicator<Num> getIndicator(IndicatorType key) {
        return indicatorMap.get(key);
    }

    public boolean hasIndicator(IndicatorType key) {
        return indicatorMap.containsKey(key);
    }

    public boolean hasInterval(CandleInterval other) {
        return this.interval.equals(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetData assetData = (AssetData) o;

        if (!info.equals(assetData.info)) return false;
        return interval == assetData.interval;
    }

    @Override
    public int hashCode() {
        int result = info.getTicker().hashCode();
        result = 31 * result + interval.hashCode();
        return result;
    }
}