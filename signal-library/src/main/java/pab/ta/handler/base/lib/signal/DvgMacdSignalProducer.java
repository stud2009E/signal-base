package pab.ta.handler.base.lib.signal;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.HighestValueIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.indicators.helpers.LowestValueIndicator;
import org.ta4j.core.num.Num;
import pab.ta.handler.base.lib.asset.AssetData;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

import static pab.ta.handler.base.lib.asset.Direction.BUY;
import static pab.ta.handler.base.lib.asset.Direction.SELL;
import static pab.ta.handler.base.lib.indicator.IndicatorType.DVG_MACD;
import static pab.ta.handler.base.lib.indicator.IndicatorType.MACD;


public class DvgMacdSignalProducer extends AbstractSignalProducer {

    private static final int LOOK_BACK = 5;

    public DvgMacdSignalProducer() {
        super(DVG_MACD);
    }

    @Override
    protected List<AssetData> filterDataForSignal(List<AssetData> assetDataList) {
        return assetDataList.stream()
                .filter(assetData -> assetData.hasIndicator(MACD))
                .toList();
    }

    @Override
    protected List<Signal> produceSignals(List<AssetData> assetDataList) {
        List<Signal> signals = new LinkedList<>();

        assetDataList.forEach(assetData -> {
            Indicator<Num> macd = assetData.getIndicator(DVG_MACD);
            BarSeries series = macd.getBarSeries();

            var endIndex = series.getEndIndex();
            var currentIndex = endIndex - 1;


            if (isSwingHigh(macd, currentIndex, LOOK_BACK)) {
                int prevHighIndex = findPreviousSwingHigh(macd, currentIndex, LOOK_BACK);
                if (prevHighIndex != -1) {
                    Num prevHighPrice = getHighestPrice(prevHighIndex, series, LOOK_BACK);
                    Num currHighPrice = getHighestPrice(currentIndex, series, LOOK_BACK);

                    boolean isMacdDown = macd.getValue(prevHighIndex).isGreaterThan(macd.getValue(currentIndex));
                    boolean isPriceUp = prevHighPrice.isLessThan(currHighPrice);

                    if (isMacdDown && isPriceUp) {
                        //regular bearish
                        signals.add(new Signal()
                                .setTicker(assetData.getInfo().getTicker())
                                .setInterval(assetData.getInterval())
                                .setDirection(SELL)
                                .addType(getTypes())
                                .setName("DVG MACD")
                                .setCreatedAt(ZonedDateTime.now()));
                    }

                    boolean isMacdUp = macd.getValue(prevHighIndex).isLessThan(macd.getValue(currentIndex));
                    boolean isPriceDown = prevHighPrice.isGreaterThan(currHighPrice);

                    if (isMacdUp && isPriceDown) {
                        //hidden bearish
                        signals.add(new Signal()
                                .setTicker(assetData.getInfo().getTicker())
                                .setInterval(assetData.getInterval())
                                .setDirection(SELL)
                                .addType(getTypes())
                                .setName("DVG MACD HIDDEN")
                                .setCreatedAt(ZonedDateTime.now()));
                    }
                }
            }

            if (isSwingLow(macd, currentIndex, LOOK_BACK)) {
                int prevLowIndex = findPreviousSwingLow(macd, currentIndex, LOOK_BACK);
                if (prevLowIndex != -1) {

                    Num prevLowPrice = getLowestPrice(prevLowIndex, series, LOOK_BACK);
                    Num currLowPrice = getLowestPrice(currentIndex, series, LOOK_BACK);

                    boolean isMacdUp = macd.getValue(prevLowIndex).isLessThan(macd.getValue(currentIndex));
                    boolean isPriceDown = prevLowPrice.isGreaterThan(currLowPrice);

                    if (isMacdUp && isPriceDown) {
                        //regular bullish
                        signals.add(new Signal()
                                .setTicker(assetData.getInfo().getTicker())
                                .setInterval(assetData.getInterval())
                                .setDirection(BUY)
                                .addType(getTypes())
                                .setName("DVG MACD")
                                .setCreatedAt(ZonedDateTime.now()));
                    }

                    boolean isMacdDown = macd.getValue(prevLowIndex).isGreaterThan(macd.getValue(currentIndex));
                    boolean isPriceUp = prevLowPrice.isLessThan(currLowPrice);

                    if (isMacdDown && isPriceUp) {
                        //hidden bullish
                        signals.add(new Signal()
                                .setTicker(assetData.getInfo().getTicker())
                                .setInterval(assetData.getInterval())
                                .setDirection(BUY)
                                .addType(getTypes())
                                .setName("DVG MACD HIDDEN")
                                .setCreatedAt(ZonedDateTime.now()));
                    }

                }
            }
        });

        return signals;
    }

    private boolean isSwingHigh(Indicator<Num> indicator, int index, int lookBack) {
        var currentValue = indicator.getValue(index);
        var endIndex = indicator.getBarSeries().getEndIndex();

        if (indicator.getValue(index).isNegativeOrZero()) {
            return false;
        }

        if (indicator.getCountOfUnstableBars() > index - lookBack) {
            return false;
        }

        //left side
        for (int i = 1; i <= lookBack; i++) {
            if (index - i < 0) {
                break;
            }

            if (indicator.getValue(index - i).isGreaterThan(currentValue)) {
                return false;
            }
        }

        //right side
        for (int i = 1; i <= lookBack; i++) {
            if (index + i > endIndex) {
                break;
            }

            if (indicator.getValue(index + i).isGreaterThan(currentValue)) {
                return false;
            }
        }

        return true;
    }

    private boolean isSwingLow(Indicator<Num> indicator, int index, int lookBack) {
        var currentValue = indicator.getValue(index);
        var endIndex = indicator.getBarSeries().getEndIndex();

        if (indicator.getValue(index).isPositiveOrZero()) {
            return false;
        }

        if (indicator.getCountOfUnstableBars() > index - lookBack) {
            return false;
        }

        //left side
        for (int i = 1; i <= lookBack; i++) {
            if (index - i < 0) {
                break;
            }

            if (indicator.getValue(index - i).isLessThan(currentValue)) {
                return false;
            }
        }

        //right side
        for (int i = 1; i <= lookBack; i++) {
            if (index + i > endIndex) {
                break;
            }

            if (indicator.getValue(index + i).isLessThan(currentValue)) {
                return false;
            }
        }

        return true;
    }


    private int findPreviousSwingHigh(Indicator<Num> indicator, int index, int lookBack) {
        int stopIndex = Math.max(indicator.getCountOfUnstableBars(), index - 6 * lookBack);

        for (int i = index - lookBack; i >= stopIndex; i--) {
            if (isSwingHigh(indicator, i, lookBack)) {
                return i;
            }
        }

        return -1;
    }

    private int findPreviousSwingLow(Indicator<Num> indicator, int index, int lookBack) {
        int stopIndex = Math.max(indicator.getCountOfUnstableBars(), index - 6 * lookBack);

        for (int i = index - lookBack; i >= stopIndex; i--) {
            if (isSwingLow(indicator, i, lookBack)) {
                return i;
            }
        }

        return -1;
    }


    private Num getHighestPrice(int index, BarSeries series, int lookBack) {
        var indicator = new HighestValueIndicator(new HighPriceIndicator(series), lookBack);

        var endIndex = series.getEndIndex();
        var startIndex = index + lookBack / 2;

        if (startIndex > endIndex) {
            startIndex = endIndex - 1;
            indicator = new HighestValueIndicator(new HighPriceIndicator(series), lookBack / 2);
        }

        return indicator.getValue(startIndex);
    }

    private Num getLowestPrice(int index, BarSeries series, int lookBack) {
        var indicator = new LowestValueIndicator(new LowPriceIndicator(series), lookBack);

        var endIndex = series.getEndIndex();
        var startIndex = index + lookBack / 2;

        if (startIndex > endIndex) {
            startIndex = endIndex - 1;
            indicator = new LowestValueIndicator(new LowPriceIndicator(series), lookBack / 2);
        }

        return indicator.getValue(startIndex);
    }


}
