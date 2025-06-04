package pab.ta.handler.base.lib.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import pab.ta.handler.base.lib.asset.BaseTimeFrame;
import pab.ta.handler.base.lib.asset.CandleInterval;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Slf4j
public class BaseTaskStarter implements TaskStarter {

    private final TaskHandler taskHandler;

    @Scheduled(cron = "${cron.task.1H}")
    @Override
    public void runTask1Hour() {
        ZonedDateTime to = ZonedDateTime.now();
        ZonedDateTime from = to.minusWeeks(1);

        log.info("Task starts with period {}", CandleInterval.HOUR_1);

        taskHandler.process(new BaseTimeFrame(CandleInterval.HOUR_1, from, to));
    }

    @Scheduled(cron = "${cron.task.2H}")
    @Override
    public void runTask2Hour() {
        ZonedDateTime to = ZonedDateTime.now();
        ZonedDateTime from = to.minusWeeks(2);

        log.info("Task starts with period {}", CandleInterval.HOUR_2);

        taskHandler.process(new BaseTimeFrame(CandleInterval.HOUR_2, from, to));
    }

    @Scheduled(cron = "${cron.task.4H}")
    @Override
    public void runTask4Hour() {
        ZonedDateTime to = ZonedDateTime.now();
        ZonedDateTime from = to.minusWeeks(4);

        log.info("Task starts with period {}", CandleInterval.HOUR_4);

        taskHandler.process(new BaseTimeFrame(CandleInterval.HOUR_4, from, to));
    }

    @Scheduled(cron = "${cron.task.1D}")
    @Override
    public void runTask1Day() {
        ZonedDateTime to = ZonedDateTime.now();
        ZonedDateTime from = to.minusWeeks(8);

        log.info("Task starts with period {}", CandleInterval.DAY);

        taskHandler.process(new BaseTimeFrame(CandleInterval.DAY, from, to));
    }


    @Scheduled(cron = "${cron.task.1W}")
    @Override
    public void runTask1Week() {
        ZonedDateTime to = ZonedDateTime.now();
        ZonedDateTime from = to.minusWeeks(50);

        log.info("Task starts with period {}", CandleInterval.MONTH);

        taskHandler.process(new BaseTimeFrame(CandleInterval.MONTH, from, to));
    }

    @Scheduled(cron = "${cron.task.1M}")
    @Override
    public void runTask1Month() {
        ZonedDateTime to = ZonedDateTime.now();
        ZonedDateTime from = to.minusWeeks(50);

        log.info("Task starts with period {}", CandleInterval.MONTH);

        taskHandler.process(new BaseTimeFrame(CandleInterval.MONTH, from, to));
    }

}