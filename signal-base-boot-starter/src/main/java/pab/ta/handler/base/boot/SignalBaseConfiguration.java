package pab.ta.handler.base.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pab.ta.handler.base.lib.signal.*;

import java.util.List;

@Configuration
@Slf4j
public class SignalBaseConfiguration {


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