package pab.ta.handler.base.provider;

import pab.ta.handler.base.asset.AssetInfo;

import java.util.List;

public interface AssetInfoSearchProvider {
    /**
     * provide assets
     *
     * @return list of assets
     */
    List<AssetInfo> search(String query);
}