package pab.ta.handler.base.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pab.ta.handler.base.lib.provider.AssetInfoProvider;
import pab.ta.handler.base.lib.provider.DataProvider;
import pab.ta.handler.base.lib.task.*;

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
    public DataTaskHandler taskHandler(DataStore store, AssetInfoProvider assetInfoProvider,
                                       DataProvider dataProvider) {
        log.info("Bean 'taskHandler' created");

        return new DataTaskHandler(store, assetInfoProvider, dataProvider);
    }

    @Bean
    @ConditionalOnMissingBean
    public DataStore dataStore() {
        log.info("Bean 'dataStore' created");

        return new DataStore();
    }

    @Bean
    @ConditionalOnMissingBean
    public SignalStore signalStore() {
        log.info("Bean 'signalStore' created");

        return new SignalStore();
    }
}