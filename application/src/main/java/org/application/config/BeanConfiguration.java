package org.application.config;


import org.application.adapters.batch.BatchTondeuseProcessor;
import org.domain.service.TondeuseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public TondeuseService tondeuseService() {
        return new TondeuseService();
    }

    @Bean
    public BatchTondeuseProcessor batchTondeuseProcessor(TondeuseService tondeuseService) {
        return new BatchTondeuseProcessor(tondeuseService);
    }
}
