package pab.ta.handler.base.lib.asset;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AssetInfo {
    @EqualsAndHashCode.Include
    private final String id;
    
    @EqualsAndHashCode.Include
    private final String ticker;

    @EqualsAndHashCode.Include
    private final AssetType type;

    private final String description;
}