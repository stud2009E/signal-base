package pab.ta.handler.base.lib.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

import java.util.LinkedList;
import java.util.List;

/**
 * Utils.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    /**
     * Gets last 5 indicator values.
     *
     * @param indicator indicator
     * @return values of indicator
     */
    public static List<Num> getLast5Values(Indicator<Num> indicator) {
        return getLastValues(indicator, 5);
    }

    /**
     * Gets last indicator values.
     *
     * @param indicator indicator
     * @param count     count of bars
     * @return values of indicator
     */
    public static List<Num> getLastValues(Indicator<Num> indicator, int count) {
        List<Num> last = new LinkedList<>();

        if (!indicator.isStable()) {
            return List.of();
        }

        int unstableCount = indicator.getCountOfUnstableBars();
        var endIndex = indicator.getBarSeries().getEndIndex();
        var beginIndex = indicator.getBarSeries().getBeginIndex();

        if (unstableCount > endIndex - count || beginIndex < endIndex - count) {
            return List.of();
        }

        for (int i = 0; i < count; i++) {
            last.addFirst(indicator.getValue(endIndex - i));
        }

        return last;
    }

}
