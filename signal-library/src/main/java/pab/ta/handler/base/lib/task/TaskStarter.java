package pab.ta.handler.base.lib.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.ta4j.core.BarSeries;
import pab.ta.handler.base.lib.asset.TimeFrame;
import pab.ta.handler.base.lib.provider.AssetInfoProvider;
import pab.ta.handler.base.lib.provider.SeriesProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
public class TaskStarter implements ITaskStarter {

    private final SeriesProvider seriesProvider;
    private final AssetInfoProvider infoProvider;
    private final List<SeriesProcessor> seriesProcessors;
    private final List<TimeFrame> timeframes;

    @Scheduled(fixedDelayString = "${task.delay.seconds}", timeUnit = TimeUnit.SECONDS)
    @Override
    public void runTask() {
        //todo event start and end task
        infoProvider
                .info()
                .forEach(assetInfo -> {

                    for (var processor : seriesProcessors) {
                        processor.beforeProcess(assetInfo);

                        for (var timeFrame : timeframes) {
                            BarSeries series = seriesProvider.getSeries(assetInfo, timeFrame);
                            processor.process(series, timeFrame);
                        }

                        processor.afterProcess();
                    }
                });
    }

}