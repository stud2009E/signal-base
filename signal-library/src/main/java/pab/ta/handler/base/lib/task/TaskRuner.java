package pab.ta.handler.base.lib.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ta4j.core.BarSeries;
import pab.ta.handler.base.lib.asset.AssetData;
import pab.ta.handler.base.lib.asset.CandleInterval;
import pab.ta.handler.base.lib.asset.TimeFrame;
import pab.ta.handler.base.lib.provider.AssetInfoProvider;
import pab.ta.handler.base.lib.provider.SeriesProvider;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class TaskRuner {

    private final AssetInfoProvider infoProvider;
    private final SeriesProvider seriesProvider;

    private final List<CandleInterval> candleIntervals;
    private final CandleHelper helper;
    private final List<AssetDataProcessor> dataProcessors;


    public void run(ZonedDateTime to) {
        infoProvider.info().forEach(assetInfo -> {
            var assetDataList = new ArrayList<AssetData>();

            for (var interval : candleIntervals) {
                var from = helper.calculateFrom(to, interval);
                var tf = new TimeFrame(interval, from, to);

                BarSeries series = seriesProvider.getSeries(assetInfo, tf);

                if (series.isEmpty()) {
                    log.info("Data series {} is empty", series.getName());
                    continue;
                }

                var assetData = AssetData.builder()
                        .info(assetInfo)
                        .timeFrame(tf)
                        .barSeries(series)
                        .createdAt(ZonedDateTime.now())
                        .build();
                assetDataList.add(assetData);
            }

            dataProcessors.forEach(dataProcessor -> dataProcessor.process(assetDataList));
        });
    }
}