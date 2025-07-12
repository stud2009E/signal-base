package pab.ta.handler.base.lib.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import pab.ta.handler.base.lib.asset.AssetData;
import pab.ta.handler.base.lib.asset.CandleInterval;
import pab.ta.handler.base.lib.asset.TimeFrame;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Slf4j
public class TaskStarter implements ITaskStarter {

    private final ITaskHandler taskHandler;
    private final IStore<AssetData> dataStore;

    @Scheduled(cron = "${cron.task.4H}")
    @Override
    public void runTask4Hour() {
        ZonedDateTime to = ZonedDateTime.now();
        ZonedDateTime from = to.minusWeeks(4);

        log.info("TASK START: period {}", CandleInterval.H4);

        taskHandler.process(new TimeFrame(CandleInterval.H4, from, to));
    }

    @Scheduled(cron = "${cron.task.1D}")
    @Override
    public void runTask1Day() {
        ZonedDateTime to = ZonedDateTime.now();
        ZonedDateTime from = to.minusWeeks(8);

        log.info("TASK START: period {}", CandleInterval.DAY);

        taskHandler.process(new TimeFrame(CandleInterval.DAY, from, to));
    }

    @Scheduled(cron = "${cron.task.clear}")
    @Override
    public void runTaskClear() {
        log.info("TASK START: clear");

        dataStore.clear();
    }

}