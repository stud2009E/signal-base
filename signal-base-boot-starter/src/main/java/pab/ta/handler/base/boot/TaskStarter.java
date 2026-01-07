package pab.ta.handler.base.boot;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import pab.ta.handler.base.lib.task.TaskRuner;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class TaskStarter {

    private final TaskRuner runer;

    @Scheduled(fixedDelayString = "${signal.task.delay.seconds}", timeUnit = TimeUnit.SECONDS)
    public void start() {
        runer.run();
    }

}
