package pab.ta.handler.base.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pab.ta.handler.base.lib.asset.CandleInterval;
import pab.ta.handler.base.lib.provider.AssetInfoProvider;
import pab.ta.handler.base.lib.provider.SeriesProvider;
import pab.ta.handler.base.lib.signal.*;
import pab.ta.handler.base.lib.task.AssetDataProcessor;
import pab.ta.handler.base.lib.task.CandleHelper;
import pab.ta.handler.base.lib.task.CandleHelperImpl;
import pab.ta.handler.base.lib.task.TaskRuner;

import java.util.Comparator;
import java.util.List;

@Configuration
@Slf4j
public class SignalBaseConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CandleHelper fromCalculator() {
        log.debug("Time from calculator is created");

        return new CandleHelperImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public TaskStarter taskStarter(@Autowired TaskRuner taskRuner) {
        log.debug("Task starter is created");

        return new TaskStarter(taskRuner);
    }

    @Bean
    @ConditionalOnMissingBean
    public SignalProcessor signalLogger() {
        log.debug("Signal processor is created");

        return (assetInfo, signals) -> {
            log.info("{} - {}", assetInfo.getTicker(), assetInfo.getType());

            signals.stream()
                    .sorted(Comparator.comparing(Signal::getInterval))
                    .forEach(signal ->
                            log.info("{} {} {}", signal.getInterval(), signal.getName(), signal.getDirection()));
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public TaskRuner taskRuner(
            @Autowired AssetInfoProvider infoProvider,
            @Autowired SeriesProvider seriesProvider,
            @Value("${signal.task.intervals}") List<CandleInterval> candleIntervals,
            @Autowired CandleHelper fromCalculator,
            @Autowired List<AssetDataProcessor> assetDataProcessors
    ) {
        log.debug("Task runner for {} is created", candleIntervals);

        return new TaskRuner(infoProvider, seriesProvider, candleIntervals,
                fromCalculator, assetDataProcessors);
    }

    @Bean
    @ConditionalOnMissingBean
    public List<AssetDataProcessor> signalProducers(@Autowired SignalProcessor signalProcessor) {
        log.debug("Signal producers are created");

        return List.of(
                new RsiSignalProducer(signalProcessor),
                new MfiSignalProducer(signalProcessor),
                new CciSignalProducer(signalProcessor),
                new MacdSignalProducer(signalProcessor),
                new DvgMacdSignalProducer(signalProcessor),
                new AdxSignalProducer(signalProcessor),
                new BBLowSignalProducer(signalProcessor),
                new BBUpSignalProducer(signalProcessor)
        );
    }
}