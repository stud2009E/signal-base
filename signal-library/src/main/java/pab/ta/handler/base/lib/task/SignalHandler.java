package pab.ta.handler.base.lib.task;

import lombok.RequiredArgsConstructor;
import pab.ta.handler.base.lib.asset.AssetData;
import pab.ta.handler.base.lib.asset.AssetInfo;
import pab.ta.handler.base.lib.signal.AbstractSignalProducer;

import java.util.List;

@RequiredArgsConstructor
public class SignalHandler implements ISignalHandler {

    private final DataStore dataStore;
    private final SignalStore signalStore;
    private final List<AbstractSignalProducer> producers;

    @Override
    public void process(AssetInfo assetInfo) {
        List<AssetData> dataList = dataStore.get(assetInfo.getTicker());

        producers.stream()
                .flatMap(producer -> producer.getSignals(dataList).stream())
                .forEach(signalStore::put);
    }
}
