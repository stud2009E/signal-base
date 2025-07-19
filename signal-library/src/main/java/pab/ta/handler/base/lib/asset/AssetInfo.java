package pab.ta.handler.base.lib.asset;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AssetInfo {

    private final String id;

    private final String ticker;

    private final AssetType type;

    private final String description;
}