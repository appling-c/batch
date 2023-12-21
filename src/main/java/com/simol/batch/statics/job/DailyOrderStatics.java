package com.simol.batch.statics.job;

import com.simol.batch.statics.domain.entity.OrderEntity;
import com.simol.batch.statics.repository.OrderEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Configuration
@Slf4j
@RequiredArgsConstructor
public class DailyOrderStatics {
    private final OrderEntityRepository orderEntityRepository;

    @Bean
    public Job staticsJob(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("staticsStep") Step staticsStep) {
        return new JobBuilder("dailyOrderStaticsJob", jobRepository)
                .start(staticsStep)
                .build();
    }

    @Bean
    public Step staticsStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("staticsTasklet") Tasklet staticsTasklet) {
        return new StepBuilder("dailyOrderStaticsStep", jobRepository)
                .tasklet(staticsTasklet, transactionManager)
                .build();
    }

    @Bean
    public Tasklet staticsTasklet() {
        return (contribution, chunkContext) -> {
            log.info("statics job 실행...");
            StepContext stepContext = chunkContext.getStepContext();
            Map<String, Object> jobParameters = stepContext.getJobParameters();
            String date = jobParameters.get("date").toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate now = LocalDate.parse(date, formatter);
            int year = now.getYear();
            int month = now.getMonthValue();
            int day = now.getDayOfMonth();

            LocalDateTime startDateTime = LocalDateTime.of(year, month, day, 0, 0, 0);
            LocalDateTime endDateTime = LocalDateTime.of(year, month, day, 23, 59, 59);
            List<OrderEntity> all = orderEntityRepository.findByCreatedAtBetween(startDateTime, endDateTime);
            //todo 옵션 리스트도 불러와야 함

            //todo 옵션 1개를 상품 1개로 봐야 됨
            System.out.println("테스트");


            return RepeatStatus.FINISHED;
        };
    }
}
