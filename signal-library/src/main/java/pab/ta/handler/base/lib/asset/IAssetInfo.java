package pab.ta.handler.base.lib.asset;

import java.util.Map;


/**
 * Represents information about an asset in a financial or asset management system.
 * This interface provides methods to access and manipulate asset properties and metadata.
 */
public interface IAssetInfo {
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
}