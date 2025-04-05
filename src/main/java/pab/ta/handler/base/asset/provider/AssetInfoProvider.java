package pab.ta.handler.base.asset.provider;

import pab.ta.handler.base.asset.AssetInfo;

import java.util.List;

public interface AssetInfoProvider {
    /**
     * provide assets
     *
     * @return list of assets
     */
    List<AssetInfo> info();
}