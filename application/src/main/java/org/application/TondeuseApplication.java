package org.application;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TondeuseApplication {

    @Qualifier("tondeuseJob")
    private final Job job;
    private final JobLauncher jobLauncher;

    public TondeuseApplication(Job job, JobLauncher jobLauncher) {
        this.job = job;
        this.jobLauncher = jobLauncher;
    }

    public static void main(String[] args) {
        SpringApplication.run(TondeuseApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            // Lancer le job
            JobExecution jobExecution = jobLauncher.run(job, new JobParameters());

            // Afficher le statut du job
            System.out.println("Job Status : " + jobExecution.getStatus());
        };
    }

}
