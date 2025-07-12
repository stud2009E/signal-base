package pab.ta.handler.base.lib.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pab.ta.handler.base.lib.asset.AssetData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
public class DataStore implements IStore<AssetData> {

    private final Set<AssetData> cache = new HashSet<>();

    @Override
    public void put(AssetData signal) {
        //to update createdAt field of signal
        cache.remove(signal);

        log.info("Put into store signal {}", signal.toString());

        cache.add(signal);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public List<AssetData> get() {
        return cache.stream().toList();
    }

}
