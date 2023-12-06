package com.simol.batch.statics.job;

import com.simol.batch.statics.domain.OrderItem;
import com.simol.batch.statics.repository.OrderItemRepository;
import com.simol.batch.statics.repository.OrderRepository;
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
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class DailyOrderStatics {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Bean
    public Job staticsJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("dailyOrderStaticsJob", jobRepository)
                .start(staticsStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step staticsStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("dailyOrderStaticsStep", jobRepository)
                .tasklet(staticsTasklet(), transactionManager)
                .build();
    }

    public Tasklet staticsTasklet() {
        return (contribution, chunkContext) -> {
            log.info("statics job 실행...");

            List<Order> orderList = orderRepository.findAllGtNow();
            List<Long> orderIdList = orderList.stream().mapToLong(Order::getId).boxed().collect(Collectors.toList());
            List<OrderItem> orderItemList = orderItemRepository.findAllByOrderId(orderIdList);

            return RepeatStatus.FINISHED;
        };
    }
}
