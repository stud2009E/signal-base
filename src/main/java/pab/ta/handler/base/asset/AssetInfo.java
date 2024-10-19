package pab.ta.handler.base.asset;

import java.util.Map;

public interface AssetInfo {
    String id();

    String ticker();

    AssetType type();

    String description();

    Map<String, Object> properties();

    Object addProperty(String key, Object value);
}