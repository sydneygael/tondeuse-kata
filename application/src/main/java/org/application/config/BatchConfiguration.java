package org.application.config;

import lombok.extern.slf4j.Slf4j;
import org.application.adapters.batch.BatchTondeuseProcessor;
import org.application.adapters.batch.BatchTondeuseReader;
import org.application.adapters.batch.BatchTondeuseWriter;
import org.application.adapters.batch.output.FileWriterAdapter;
import org.application.ports.ouput.WritePort;
import org.application.usescases.DeplacerTondeuse;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;

@Slf4j
@Configuration
@EnableConfigurationProperties(BatchProperties.class)
@EnableBatchProcessing
public class BatchConfiguration {

    @Bean
    public BatchTondeuseReader reader(BatchProperties batchProperties) throws IOException {
        return new BatchTondeuseReader(
                batchProperties.inputFile()
                        .getFile(),
                true);
    }

    @Bean
    public Job tondeuseJob(final JobRepository jobRepository,
                           Step tondeuseStep) {
        return new JobBuilder("tondeuseJob", jobRepository)
                .start(tondeuseStep)
                .build();
    }

    @Bean
    ItemProcessor<String, String> processor(DeplacerTondeuse deplacerTondeuse) {
        return new BatchTondeuseProcessor(deplacerTondeuse);
    }


    @Bean
    public Step tondeuseStep(final JobRepository jobRepository,
                             PlatformTransactionManager transactionManager,
                             DeplacerTondeuse deplacerTondeuse,
                             BatchTondeuseReader reader,
                             BatchTondeuseWriter batchTondeuseWriter) {
        return new StepBuilder("tondeuseStep", jobRepository)
                .<String, String>chunk(1000, transactionManager)
                .reader(reader)
                .processor(processor(deplacerTondeuse))
                .writer(batchTondeuseWriter)
                .build();
    }

    @Bean
    public WritePort fileWriterAdapter() {
        return new FileWriterAdapter();
    }

    @Bean
    public BatchTondeuseWriter writer(BatchProperties batchProperties,
                                      WritePort fileWriterAdapter) throws IOException {
        return new BatchTondeuseWriter(
                fileWriterAdapter,
                batchProperties.resultFile()
                        .getFile());
    }

    @Bean
    public CommandLineRunner run(JobLauncher jobLauncher, Job tondeuseJob) {
        return args -> {
            // Lancer le job
            JobExecution jobExecution = jobLauncher.run(tondeuseJob, new JobParameters());

            // Afficher le statut du job
            log.info("Job Status : {}", jobExecution.getStatus());
        };
    }

}
