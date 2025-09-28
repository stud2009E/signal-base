package pab.ta.handler.base.stats.counter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.function.Predicate;

@Getter
@Accessors(chain = true)
public class StatsCounter {
    private final Integer offset;

    @Setter
    private Predicate<Integer> predicate;

    private Integer count = 0;
    private Integer totalCount = 0;

    public StatsCounter(Integer offset) {
        this.offset = offset;
        predicate = i -> false;
    }


    public void write(Integer signalIndex) {
        totalCount++;

        if (predicate.test(signalIndex)) {
            count++;
        }
    }

}
