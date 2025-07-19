package pab.ta.handler.base.lib.task;

import lombok.RequiredArgsConstructor;
import pab.ta.handler.base.lib.asset.AssetData;
import pab.ta.handler.base.lib.signal.AbstractSignalProducer;

import java.util.List;

@RequiredArgsConstructor
public class SignalTaskHandler implements ISignalTaskHandler {

    private final DataStore dataStore;
    private final SignalStore signalStore;
    private final List<AbstractSignalProducer> producers;

    @Override
    public void process() {
        dataStore.getAll()
                .stream()
                .map(asset -> asset.getInfo().getTicker())
                .sorted()
                .distinct()
                .map(dataStore::get)
                .forEach(this::findSignal);
    }

    private void findSignal(List<AssetData> data) {
        producers.stream()
                .flatMap(producer -> producer.getSignals(data).stream())
                .forEach(signalStore::put);
    }
}
