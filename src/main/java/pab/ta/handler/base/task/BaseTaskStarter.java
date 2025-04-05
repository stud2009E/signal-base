package pab.ta.handler.base.task;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import pab.ta.handler.base.asset.BaseTimeFrame;
import pab.ta.handler.base.asset.CandleInterval;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class BaseTaskStarter implements TaskStarter {

    private final TaskHandler taskHandler;

    @Scheduled(cron = "${cron.task.1H}")
    @Override
    public void runTask1Hour() {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusWeeks(1);

        taskHandler.process(new BaseTimeFrame(CandleInterval.HOUR_1, from, to));
    }

    @Scheduled(cron = "${cron.task.2H}")
    @Override
    public void runTask2Hour() {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusWeeks(2);

        taskHandler.process(new BaseTimeFrame(CandleInterval.HOUR_2, from, to));
    }

    @Scheduled(cron = "${cron.task.4H}")
    @Override
    public void runTask4Hour() {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusWeeks(4);

        taskHandler.process(new BaseTimeFrame(CandleInterval.HOUR_4, from, to));
    }

    @Scheduled(cron = "${cron.task.1D}")
    @Override
    public void runTask1Day() {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusWeeks(8);

        taskHandler.process(new BaseTimeFrame(CandleInterval.DAY, from, to));
    }


    @Scheduled(cron = "${cron.task.1W}")
    @Override
    public void runTask1Week() {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusWeeks(50);

        taskHandler.process(new BaseTimeFrame(CandleInterval.WEEK, from, to));
    }

    @Scheduled(cron = "${cron.task.1M}")
    @Override
    public void runTask1Month() {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusWeeks(50);

        taskHandler.process(new BaseTimeFrame(CandleInterval.MONTH, from, to));
    }

}