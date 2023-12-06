package com.simol.batch.job.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class TestJob {

    @Bean
    public Job testSimpleJob(PlatformTransactionManager transactionManager, JobRepository jobRepository) {
        return new JobBuilder("testSimpleJob", jobRepository)
                .start(testSimpleStep(transactionManager, jobRepository))
                .build();
    }

    @Bean
    public Step testSimpleStep(PlatformTransactionManager transactionManager, JobRepository jobRepository) {

        return new StepBuilder("testSimpleStep", jobRepository)
                .tasklet(testTasklet(), transactionManager)
                .build();
    }

    public Tasklet testTasklet() {
        return (contribution, chunkContext) -> {
            log.info("test");
            return RepeatStatus.FINISHED;
        };
    }
}
