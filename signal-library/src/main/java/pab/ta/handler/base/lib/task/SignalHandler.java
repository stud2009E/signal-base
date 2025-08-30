package pab.ta.handler.base.lib.task;

import lombok.RequiredArgsConstructor;
import pab.ta.handler.base.lib.asset.AssetData;
import pab.ta.handler.base.lib.asset.AssetInfo;
import pab.ta.handler.base.lib.signal.AbstractSignalProducer;
import pab.ta.handler.base.lib.signal.Signal;

import java.util.Collection;
import java.util.Comparator;
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
                .map(producer -> producer.getSignals(dataList))
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Signal::getInterval))
                .forEach(signalStore::put);
    }
}
