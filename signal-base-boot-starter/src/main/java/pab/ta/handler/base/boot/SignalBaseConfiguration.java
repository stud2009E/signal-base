package pab.ta.handler.base.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pab.ta.handler.base.lib.provider.AssetInfoProvider;
import pab.ta.handler.base.lib.provider.DataProvider;
import pab.ta.handler.base.lib.signal.*;
import pab.ta.handler.base.lib.task.*;

import java.util.List;

@Configuration
@Slf4j
public class SignalBaseConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ITaskStarter taskStarter(IDataTaskHandler dataTaskHandler, ISignalTaskHandler signalTaskHandler) {
        log.info("Bean 'taskStarter' created");

        return new TaskStarter(dataTaskHandler, signalTaskHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    public DataTaskHandler dataTaskHandler(DataStore store, AssetInfoProvider assetInfoProvider, DataProvider dataProvider) {
        log.info("Bean 'dataTaskHandler' is created");

        return new DataTaskHandler(store, assetInfoProvider, dataProvider);
    }

    @Bean
    @ConditionalOnMissingBean
    public SignalTaskHandler signalTaskHandler(DataStore dataStore, SignalStore signalStore) {
        log.info("Bean 'signalTaskHandler' is created");

        return new SignalTaskHandler(dataStore, signalStore, signalProducers());
    }

    @Bean
    @ConditionalOnMissingBean
    public DataStore dataStore() {
        log.info("Bean 'dataStore' is created");

        return new DataStore();
    }

    @Bean
    @ConditionalOnMissingBean
    public SignalStore signalStore() {
        log.info("Bean 'signalStore' is created");

        return new SignalStore();
    }

    @Bean
    @ConditionalOnMissingBean
    public List<AbstractSignalProducer> signalProducers() {
        log.info("Bean 'signalProducers' is created");

        return List.of(
                new RsiSignalProducer(),
                new MfiSignalProducer(),
                new CciSignalProducer(),
                new MacdSignalProducer(),
                new DvgMacdSignalProducer(),
                new AdxSignalProducer(),
                new BBLowSignalProducer(),
                new BBUpSignalProducer()
        );
    }
}