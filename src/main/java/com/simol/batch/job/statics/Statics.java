package com.simol.batch.job.statics;

import com.simol.batch.job.statics.domain.Order;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class Statics {
    private final StaticsRepository staticsRepository;

    @Bean
    public Job staticsJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("staticsJob", jobRepository)
                .start(staticsStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step staticsStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("staticsStep", jobRepository)
                .tasklet(staticsTasklet(), transactionManager)
                .build();
    }

    public Tasklet staticsTasklet() {
        return (contribution, chunkContext) -> {
            log.info("statics job 실행...");

            List<Order> orderList = staticsRepository.get();
            for(Order order : orderList) {
                System.out.println("order_id = " + order.getId());
            }

            return RepeatStatus.FINISHED;
        };
    }
}
