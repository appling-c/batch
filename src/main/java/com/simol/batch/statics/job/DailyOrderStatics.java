package com.simol.batch.statics.job;

import com.simol.batch.statics.domain.Option;
import com.simol.batch.statics.domain.OrderItem;
import com.simol.batch.statics.domain.Product;
import com.simol.batch.statics.domain.ProductType;
import com.simol.batch.statics.repository.OptionRepository;
import com.simol.batch.statics.repository.OrderItemRepository;
import com.simol.batch.statics.repository.OrderRepository;
import com.simol.batch.job.statics.domain.Order;
import com.simol.batch.statics.repository.ProductRepository;
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
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

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

            if(!orderIdList.isEmpty()){
                List<OrderItem> orderItemList = orderItemRepository.findAllByOrderId(orderIdList);
                List<Long> orderProductIdList = orderItemList.stream().mapToLong(OrderItem::getOrderProductId).boxed().collect(Collectors.toList());

                List<Product> productList = productRepository.findAllById(orderProductIdList);
                List<Product> normalProductList = productList.stream().filter(p -> p.getType() == ProductType.NORMAL).collect(Collectors.toList());

                //todo 옵션 리스트도 불러와야 함
                List<Product> optionProductList = productList.stream().filter(p -> p.getType() == ProductType.OPTION).collect(Collectors.toList());
                List<Long> optionProductIdList = optionProductList.stream().mapToLong(Product::getId).boxed().collect(Collectors.toList());
                List<Option> optionList = optionRepository.findAllByProduct(optionProductIdList);

                //todo 옵션 1개를 상품 1개로 봐야 됨
                System.out.println("테스트");
            }


            return RepeatStatus.FINISHED;
        };
    }
}
