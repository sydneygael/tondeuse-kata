package org.application.config;


import org.application.adapters.batch.output.FileWriterAdapter;
import org.domain.factory.TondeuseCommandeFactoryImpl;
import org.domain.service.TondeuseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public TondeuseService tondeuseService() {
        return new TondeuseService(new TondeuseCommandeFactoryImpl());
    }
}
