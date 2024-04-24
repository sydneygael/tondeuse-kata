package org.application.adapters;

import org.domain.service.TondeuseService;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Classe représentant la configuration minimale de test
 */
@Configuration
@EnableBatchProcessing
public class AppTestConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("/org/springframework/batch/core/schema-h2.sql")
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public TondeuseService tondeuseService() {
        return new TondeuseService();
    }

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        var localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        var hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("tondeusePersistenceUnit");
        localContainerEntityManagerFactoryBean.setPackagesToScan("org.domain");
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public JobRepository jobRepository() throws Exception {
        var jobrepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobrepositoryFactoryBean.setDataSource(dataSource());
        jobrepositoryFactoryBean.setTransactionManager(transactionManager());
        jobrepositoryFactoryBean.afterPropertiesSet();
        return jobrepositoryFactoryBean.getObject();
    }
}
