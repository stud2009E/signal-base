package pab.ta.handler.base.lib.provider;

import pab.ta.handler.base.lib.asset.AssetInfo;

import java.util.List;


/**
 * Provides access to information about available financial assets/instruments.
 */
public interface AssetInfoProvider {

    /**
     * Retrieves information about all supported assets
     *
     * @return List of asset information objects (never null)
     */
    List<AssetInfo> info();
}