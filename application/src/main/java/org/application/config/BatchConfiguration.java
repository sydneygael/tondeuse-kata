package org.application.config;

import org.domain.ports.input.MoveTondeusePort;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfiguration {

    private final JobBuilder jobBuilder; // Use JobBuilder
    private final StepBuilder stepBuilder; // Use StepBuilder

    private final MoveTondeusePort moveTondeusePort;

    public BatchConfiguration(JobBuilder jobBuilder,
                              StepBuilder stepBuilder,
                              MoveTondeusePort moveTondeusePort) {
        this.jobBuilder = jobBuilder;
        this.stepBuilder = stepBuilder;
        this.moveTondeusePort = moveTondeusePort;
    }

    @Bean
    public FlatFileItemReader<String> reader() {
        return new FlatFileItemReaderBuilder<String>()
                .name("fileReader")
                .resource(new ClassPathResource("path/to/your/file.txt"))
                .lineMapper(new PassThroughLineMapper())
                .build();
    }


    @Bean
    public Job myJob(JobRepository jobRepository, Step step) {
        return new JobBuilder("tondeuseJob", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("tondeuseStep", jobRepository)
                .chunk(100, transactionManager)
                .build();
    }


    // Implement reader(), tondeuseItemProcessor(), and writer() methods here...

}
