package pab.ta.handler.base.lib.asset;

import org.ta4j.core.num.Num;
import pab.ta.handler.base.lib.indicator.SupportedIndicator;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

/**
 * Provides access to asset data including indicator values and metadata.
 */
public interface IAssetData {

    /**
     * @return Asset information (name, symbol, etc.)
     */
    IAssetInfo getInfo();

    /**
     * @return Time interval between candles
     */
    CandleInterval getInterval();

    /**
     * @return Map of all indicator values (key: indicator type, value: calculated values)
     */
    Map<SupportedIndicator, List<Num>> getIndicatorValues();

    /**
     * @return Timestamp when this data was created
     */
    ZonedDateTime getCreatedAt();

    /**
     * Stores indicator values for the specified indicator type
     *
     * @param key   Indicator type
     * @param value Calculated values
     */
    void putValue(SupportedIndicator key, List<Num> value);

    /**
     * Retrieves indicator values for the specified indicator type
     *
     * @param key Indicator type
     * @return List of calculated values or null if not found
     */
    List<Num> getValues(SupportedIndicator key);
}
