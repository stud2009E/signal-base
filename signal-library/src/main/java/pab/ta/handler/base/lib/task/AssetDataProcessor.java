package pab.ta.handler.base.lib.task;

import pab.ta.handler.base.lib.asset.AssetData;
import pab.ta.handler.base.lib.indicator.IndicatorType;

import java.util.List;
import java.util.Set;

public interface AssetDataProcessor {

    void process(List<AssetData> assetDataList);

    default Set<IndicatorType> getIndicatorTypes() {
        return Set.of();
    }
}
