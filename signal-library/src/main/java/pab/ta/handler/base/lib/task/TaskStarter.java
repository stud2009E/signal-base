package pab.ta.handler.base.lib.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import pab.ta.handler.base.lib.asset.AssetData;
import pab.ta.handler.base.lib.asset.CandleInterval;
import pab.ta.handler.base.lib.asset.TimeFrame;
import pab.ta.handler.base.lib.provider.AssetInfoProvider;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
public class TaskStarter implements ITaskStarter {

    private final IDataHandler dataHandler;
    private final ISignalHandler signalHandler;
    private final AssetInfoProvider infoProvider;

    @Scheduled(fixedDelayString = "${task.delay.seconds}", timeUnit = TimeUnit.SECONDS)
    @Override
    public void runTask() {

        infoProvider
                .info()
                .forEach(assetInfo -> {
                    for (var timeFrame : getTimeframes()) {
                        var assetData = AssetData.builder()
                                .info(assetInfo)
                                .timeFrame(timeFrame)
                                .createdAt(timeFrame.getTo())
                                .build();

                        dataHandler.process(assetData);
                    }

                    signalHandler.process(assetInfo);
                });
    }

    private List<TimeFrame> getTimeframes() {
        var to = ZonedDateTime.now();

        return List.of(
                new TimeFrame(CandleInterval.H1, to.minusWeeks(1), to),
                new TimeFrame(CandleInterval.H4, to.minusWeeks(4), to),
                new TimeFrame(CandleInterval.DAY, to.minusWeeks(8), to));
    }
}