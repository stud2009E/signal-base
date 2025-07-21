package pab.ta.handler.base.lib.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pab.ta.handler.base.lib.asset.AssetData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class DataStore implements IStore<AssetData, String> {

    private final Set<AssetData> cache = new HashSet<>();

    @Override
    public void put(AssetData data) {
        //to update createdAt field of signal
        cache.remove(data);

        log.info("Save data {}, {}", data.getInfo().getTicker(), data.getInterval().name());

        cache.add(data);
    }

    @Override
    public List<AssetData> get(String key) {
        return cache.stream()
                .filter(assetData -> assetData.getInfo().getTicker().equals(key))
                .collect(Collectors.toList());
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public List<AssetData> getAll() {
        return cache.stream().toList();
    }

}
