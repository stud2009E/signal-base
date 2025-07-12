package pab.ta.handler.base.lib.asset;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ta4j.core.num.Num;
import pab.ta.handler.base.lib.indicator.SupportedIndicator;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@Builder
public class AssetData implements IAssetData {

    private final IAssetInfo info;

    private final CandleInterval interval;

    private final ZonedDateTime createdAt;

    private final Map<SupportedIndicator, List<Num>> indicatorValues = new HashMap<>();

    public void putValue(SupportedIndicator key, List<Num> value) {
        indicatorValues.put(key, value);
    }

    public List<Num> getValues(SupportedIndicator key) {
        return indicatorValues.get(key);
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