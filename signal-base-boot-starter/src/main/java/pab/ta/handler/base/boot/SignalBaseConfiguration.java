package pab.ta.handler.base.boot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
public class SignalBaseConfiguration {

    @Bean
    public List<SignalProducer> signalProducers() {
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
    @ConditionalOnBean(TaskHandler.class)
    @ConditionalOnMissingBean
    public TaskStarter taskStarter(TaskHandler taskHandler) {
        return new BaseTaskStarter(taskHandler);
    }

    @Bean
    @ConditionalOnBean({SignalStore.class, AssetInfoProvider.class, DataProvider.class, SignalProducer.class})
    @ConditionalOnMissingBean
    public TaskHandler taskHandler(SignalStore signalStore, AssetInfoProvider assetInfoProvider,
                                   DataProvider dataProvider, List<SignalProducer> producers) {

        return new BaseTaskHandler(signalStore, assetInfoProvider, dataProvider, producers);
    }

    @Bean
    @ConditionalOnMissingBean
    public SignalStore signalStore() {
        return new BaseSignalStore(30L);
    }
}