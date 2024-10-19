package pab.ta.handler.base.component.task;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pab.ta.handler.base.asset.BaseTimeFrame;
import pab.ta.handler.base.asset.CandleInterval;
import pab.ta.handler.base.task.TaskHandler;
import pab.ta.handler.base.task.TaskStarter;

import java.time.LocalDateTime;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class BaseTaskStarter implements TaskStarter {

    private final TaskHandler taskHandler;

    @Override
    @Scheduled(cron = "${cron.task.2H}")
    public void runTask2h() {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusWeeks(2);

        taskHandler.process(new BaseTimeFrame(CandleInterval.HOUR_2, from, to));
    }

    @Scheduled(cron = "${cron.task.4H}")
    public void runTask4h() {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusWeeks(4);

        taskHandler.process(new BaseTimeFrame(CandleInterval.HOUR_4, from, to));
    }

    @Scheduled(cron = "${cron.task.1D}")
    public void runTask1d() {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusWeeks(8);

        taskHandler.process(new BaseTimeFrame(CandleInterval.DAY, from, to));
    }


    @Scheduled(cron = "${cron.task.1W}")
    public void runTask1w() {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusWeeks(50);

        taskHandler.process(new BaseTimeFrame(CandleInterval.WEEK, from, to));
    }

}