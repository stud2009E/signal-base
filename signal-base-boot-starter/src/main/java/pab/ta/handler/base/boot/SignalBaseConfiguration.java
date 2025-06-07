package pab.ta.handler.base.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pab.ta.handler.base.lib.asset.provider.AssetInfoProvider;
import pab.ta.handler.base.lib.asset.provider.DataProvider;
import pab.ta.handler.base.lib.signal.*;
import pab.ta.handler.base.lib.task.*;

import java.util.Arrays;
import java.util.List;

@Configuration
@Slf4j
public class SignalBaseConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public List<SignalProducer> signalProducers() {

        log.info("Bean 'signalProducers' created");

        return Arrays.asList(
                new CciBuySignalProducer(),
                new CciSellSignalProducer(),
                new RsiBuySignalProducer(),
                new RsiSellSignalProducer(),
                new MfiBuySignalProducer(),
                new MfiSellSignalProducer()
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public TaskStarter taskStarter(TaskHandler taskHandler) {

        log.info("Bean 'taskStarter' created");

        return new BaseTaskStarter(taskHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    public TaskHandler taskHandler(Store signalStore, AssetInfoProvider assetInfoProvider,
                                   DataProvider dataProvider, List<SignalProducer> producers) {

        log.info("Bean 'taskHandler' created");

        return new BaseTaskHandler(signalStore, assetInfoProvider, dataProvider, producers);
    }

    @Bean
    @ConditionalOnMissingBean
    public Store signalStore() {

        log.info("Bean 'signalStore' created");

        return new BaseSignalStore();
    }
}