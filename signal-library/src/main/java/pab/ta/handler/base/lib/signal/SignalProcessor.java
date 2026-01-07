package pab.ta.handler.base.lib.signal;

import pab.ta.handler.base.lib.asset.AssetInfo;

import java.util.List;

public interface SignalProcessor {

    void process(AssetInfo assetInfo, List<Signal> signals);
}
