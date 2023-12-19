package com.simol.batch.statics.job;

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
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class DailyOrderStatic {


    @Bean
    public Job staticsJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("dailyOrderStaticJob", jobRepository)
                .start(staticsStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step staticsStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("dailyOrderStaticStep", jobRepository)
                .tasklet(staticsTasklet(), transactionManager)
                .build();
    }

    public Tasklet staticsTasklet() {
        return (contribution, chunkContext) -> {

            log.info("statics job 실행...");

            return RepeatStatus.FINISHED;
        };
    }
}
