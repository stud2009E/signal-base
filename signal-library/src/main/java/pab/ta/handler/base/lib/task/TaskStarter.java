package pab.ta.handler.base.lib.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import pab.ta.handler.base.lib.asset.CandleInterval;
import pab.ta.handler.base.lib.asset.TimeFrame;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Slf4j
public class TaskStarter implements ITaskStarter {

    private final IDataTaskHandler dataTaskHandler;
    private final ISignalTaskHandler signalTaskHandler;

    @Scheduled(fixedDelayString = "${task.delay.base}")
    @Override
    public void runTask() {
        ZonedDateTime to = ZonedDateTime.now();

        dataTaskHandler.setTimeFrame(new TimeFrame(CandleInterval.H4, to.minusWeeks(4), to));
        dataTaskHandler.process();

        dataTaskHandler.setTimeFrame(new TimeFrame(CandleInterval.DAY, to.minusWeeks(8), to));
        dataTaskHandler.process();

        signalTaskHandler.process();
    }
}