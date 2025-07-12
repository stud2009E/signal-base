package pab.ta.handler.base.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pab.ta.handler.base.lib.asset.AssetData;
import pab.ta.handler.base.lib.provider.AssetInfoProvider;
import pab.ta.handler.base.lib.provider.DataProvider;
import pab.ta.handler.base.lib.task.*;

@Configuration
@Slf4j
public class SignalBaseConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ITaskStarter taskStarter(ITaskHandler taskHandler, IStore<AssetData> store) {

        log.info("Bean 'taskStarter' created");

        return new TaskStarter(taskHandler, store);
    }

    @Bean
    @ConditionalOnMissingBean
    public ITaskHandler taskHandler(IStore<AssetData> store, AssetInfoProvider assetInfoProvider,
                                    DataProvider dataProvider) {

        log.info("Bean 'taskHandler' created");

        return new TaskHandler(store, assetInfoProvider, dataProvider);
    }

    @Bean
    @ConditionalOnMissingBean
    public IStore<AssetData> store() {

        log.info("Bean 'signalStore' created");

        return new DataStore();
    }
}