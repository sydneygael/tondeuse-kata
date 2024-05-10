package org.application.config;


import org.application.adapters.batch.BatchTondeuseProcessor;
import org.application.usescases.DeplacerTondeuse;
import org.application.usescases.UseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "org.application",
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = UseCase.class)
)
public class BeanConfiguration {

    @Bean
    public BatchTondeuseProcessor batchTondeuseProcessor(DeplacerTondeuse deplacerTondeuse) {
        return new BatchTondeuseProcessor(deplacerTondeuse);
    }
}
