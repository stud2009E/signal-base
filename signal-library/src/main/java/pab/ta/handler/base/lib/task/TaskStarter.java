package pab.ta.handler.base.lib.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import pab.ta.handler.base.lib.asset.CandleInterval;
import pab.ta.handler.base.lib.asset.TimeFrame;

import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
public class TaskStarter implements ITaskStarter {

    private final IDataTaskHandler dataTaskHandler;
    private final ISignalTaskHandler signalTaskHandler;

    @Scheduled(fixedDelayString = "${task.delay.seconds}", timeUnit = TimeUnit.SECONDS)
    @Override
    public void runTask() {
        ZonedDateTime to = ZonedDateTime.now();

        dataTaskHandler.setTimeFrame(new TimeFrame(CandleInterval.H4, to.minusWeeks(4), to));
        dataTaskHandler.process();

        signalTaskHandler.process();

        dataTaskHandler.setTimeFrame(new TimeFrame(CandleInterval.DAY, to.minusWeeks(8), to));
        dataTaskHandler.process();

        signalTaskHandler.process();
    }
}