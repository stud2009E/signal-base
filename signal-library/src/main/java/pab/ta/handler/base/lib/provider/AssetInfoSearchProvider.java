package pab.ta.handler.base.lib.provider;

import jakarta.annotation.Nonnull;
import pab.ta.handler.base.lib.asset.IAssetInfo;

import java.util.List;

/**
 * Provides search capabilities for financial assets/instruments.
 */
public interface AssetInfoSearchProvider {

    /**
     * Searches for assets matching the given query
     *
     * @param query Search string (symbol, name, or other identifier)
     * @return List of matching asset information objects, empty list if no matches found
     */
    List<IAssetInfo> search(@Nonnull String query);
}