package pab.ta.handler.base.asset;

import java.util.Map;


/**
 * Represents information about an asset in a financial or asset management system.
 * This interface provides methods to access and manipulate asset properties and metadata.
 */
public interface AssetInfo {
    /**
     * Retrieves the unique identifier of the asset.
     *
     * @return the asset's unique ID as a String
     */
    String getId();

    /**
     * Retrieves the ticker symbol associated with the asset.
     * The ticker is typically a short code used to identify the asset in trading systems.
     *
     * @return the asset's ticker symbol as a String
     */
    String getTicker();

    /**
     * Retrieves the type of the asset.
     *
     * @return the asset's type as an {@link AssetType} enum value
     */
    AssetType getType();

    /**
     * Retrieves a human-readable description of the asset.
     *
     * @return the asset's description as a String
     */
    String getDescription();

    /**
     * Retrieves a map of additional properties associated with the asset.
     * These properties may contain implementation-specific metadata or extended attributes.
     *
     * @return a Map containing key-value pairs of additional asset properties.
     * The returned Map may be unmodifiable.
     */
    Map<String, Object> getProperties();

    /**
     * Adds or updates a property for the asset.
     *
     * @param key   the property key (name) as a String
     * @param value the property value (can be any Object type)
     * @return the previous value associated with the key, or null if there was no previous value
     * @throws UnsupportedOperationException if the operation is not supported by the implementation
     * @throws IllegalArgumentException      if the key or value is invalid
     */
    Object addProperty(String key, Object value);
}