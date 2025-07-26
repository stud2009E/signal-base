package pab.ta.handler.base.lib.indicator;

import lombok.Getter;

@Getter
public enum IndicatorType {

    MA50("moving average 50"),
    MA100("moving average 100"),
    MA200("moving average 200"),

    RSI14("relative strength index 14"),
    MFI14("money flow index 14"),
    CCI14("commodity channel index 14"),

    ATR14("average true range 14"),
    ADX14("average directional index 14"),

    MACD("moving average convergence divergence 12,26 and 9"),

    VWAP("volume-weighted average price"),

    BB_LOW("bollinger bands low line 20, 2"),
    BB_UP("bollinger bands up line 20, 2");


    private final String value;

    IndicatorType(String value) {
        this.value = value;
    }

}
