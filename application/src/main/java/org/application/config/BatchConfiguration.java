package org.application.config;

import org.application.adapters.batch.BatchTondeuseProcessor;
import org.application.adapters.batch.BatchTondeuseWriter;
import org.domain.ports.ouput.WritePort;
import org.domain.service.TondeuseService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ComponentScan
@EnableBatchProcessing
public class BatchConfiguration {
    @Bean
    public FlatFileItemReader<String> reader(@Value("${file.input}") String inputFilePath) {
        return new FlatFileItemReaderBuilder<String>()
                .name("fileReader")
                .resource(new ClassPathResource(inputFilePath))
                .lineMapper(new PassThroughLineMapper())
                .build();
    }

    @Bean
    public Job tondeuseJob(final JobRepository jobRepository, @Qualifier("tondeuseStep") Step step) {
        return new JobBuilder("tondeuseJob", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    ItemProcessor<String, String> processor(TondeuseService service) {
        return new BatchTondeuseProcessor(service);
    }


    @Bean
    @Qualifier("tondeuseStep")
    public Step tondeuseStep(final JobRepository jobRepository,
                             PlatformTransactionManager transactionManager,
                             TondeuseService service,
                             FlatFileItemReader<String> reader,
                             @Qualifier("batchTondeuseWriter") BatchTondeuseWriter batchTondeuseWriter,
                             WritePort fileWriterAdapter) {
        return new StepBuilder("tondeuseStep", jobRepository)
                .<String, String>chunk(100, transactionManager)
                .reader(reader)
                .processor(processor(service))
                .writer(batchTondeuseWriter)
                .build();
    }

}
