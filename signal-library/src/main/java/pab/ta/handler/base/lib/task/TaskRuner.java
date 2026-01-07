package pab.ta.handler.base.lib.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ta4j.core.BarSeries;
import pab.ta.handler.base.lib.asset.AssetData;
import pab.ta.handler.base.lib.asset.CandleInterval;
import pab.ta.handler.base.lib.asset.TimeFrame;
import pab.ta.handler.base.lib.indicator.IndicatorFactory;
import pab.ta.handler.base.lib.provider.AssetInfoProvider;
import pab.ta.handler.base.lib.provider.SeriesProvider;
import pab.ta.handler.base.lib.signal.SignalProcessor;
import pab.ta.handler.base.lib.signal.SignalProducer;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class TaskRuner {

    private final AssetInfoProvider infoProvider;

    private final SeriesProvider seriesProvider;

    private final List<CandleInterval> candleIntervals;

    private final CandleHelper fromCalculator;

    private final List<SignalProducer> signalProducers;

    private final SignalProcessor signalProcessor;

    public void run() {
        var to = ZonedDateTime.now();

        var indicatorTypes = signalProducers.stream()
                .map(SignalProducer::getIndicatorTypes)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        infoProvider
                .info()
                .forEach(assetInfo -> {
                    var assetDataList = new ArrayList<AssetData>();

                    for (var interval : candleIntervals) {
                        var from = fromCalculator.calculate(to, interval);
                        var tf = new TimeFrame(interval, from, to);
                        BarSeries series = seriesProvider.getSeries(assetInfo, tf);

                        if (series.isEmpty()) {
                            continue;
                        }

                        var assetData = AssetData.builder()
                                .info(assetInfo)
                                .timeFrame(tf)
                                .createdAt(ZonedDateTime.now())
                                .build();

                        for (var type : indicatorTypes) {
                            assetData.putIndicator(type, IndicatorFactory.getInstance(type, series));
                        }
                        assetDataList.add(assetData);
                    }

                    var signals = signalProducers.stream()
                            .map(producer -> producer.getSignals(assetDataList))
                            .flatMap(List::stream)
                            .toList();

                    if (!signals.isEmpty()) {
                        signalProcessor.process(assetInfo, signals);
                    }
                });
    }

}