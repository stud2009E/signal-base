package pab.ta.handler.base.lib.asset.provider;

import pab.ta.handler.base.lib.asset.AssetInfo;

import java.util.List;

public interface AssetInfoSearchProvider {

    /**
     * provide assets
     *
     * @return list of assets
     */
    List<AssetInfo> search(String query);
}