package pab.ta.handler.base.lib.signal;

import pab.ta.handler.base.lib.asset.AssetData;
import pab.ta.handler.base.lib.indicator.IndicatorType;

import java.util.List;
import java.util.Set;

public interface SignalProducer {

    List<Signal> getSignals(List<AssetData> assetDataList);

    Set<IndicatorType> getIndicatorTypes();
}
