package pab.ta.handler.base.asset;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BaseAssetInfo implements AssetInfo {

    private final String id;
    private final String ticker;
    private final AssetType type;
    private final String description;
    private final Map<String, Object> properties = new HashMap<>();

    public BaseAssetInfo(String id, String ticker, AssetType type, String description) {
        this(id, ticker, type, description, new HashMap<>());
    }

    public BaseAssetInfo(String id, String ticker, AssetType type, String description, Map<String, Object> properties) {
        this.id = id;
        this.ticker = ticker;
        this.type = type;
        this.description = description;

        if (Objects.nonNull(properties)){
            this.properties.putAll(properties);
        }
    }

    public Object addProperty(String key, Object value){
        return properties.putIfAbsent(key, value);
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String ticker() {
        return ticker;
    }

    @Override
    public AssetType type() {
        return type;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public Map<String, Object> properties() {
        return Map.copyOf(properties);
    }
}