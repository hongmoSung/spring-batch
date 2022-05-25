package io.springbatch.springbatchlecture.retry.three;

import io.springbatch.springbatchlecture.jpa.Customer2;
import io.springbatch.springbatchlecture.retry.one.RetryItemProcessor;
import io.springbatch.springbatchlecture.retry.one.RetryalbeException;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Configuration
@RequiredArgsConstructor
public class RetryConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job retryJob3() {
        return jobBuilderFactory.get("retryJob3")
                .start(retryStep3())
                .build();
    }

    @Bean
    public Step retryStep3() {
        return stepBuilderFactory.get("retryStep3")
                .<String, CustomerDto>chunk(5)
                .reader(reader3())
                .processor(processor3())
                .writer(items -> items.forEach(i -> System.out.println("i = " + i)))
                .faultTolerant()
                .skip(RetryalbeException.class)
                .skipLimit(2)
                .retry(RetryalbeException.class)
                .retryLimit(2)
                .retryPolicy(retryPolicy())
                .build();
    }

    @Bean
    public ItemProcessor<String, CustomerDto> processor3() {
        return new RetryItemProcessor3();
    }

    @Bean
    public ItemReader<String> reader3() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            items.add(String.valueOf(i));
        }
        return new ListItemReader<>(items);
    }

    @Bean
    public RetryPolicy retryPolicy() {
        Map<Class<? extends Throwable>, Boolean> exceptionClass = new HashMap<>();
        exceptionClass.put(RetryalbeException.class, true);
        return new SimpleRetryPolicy(2, exceptionClass);
    }

    @Bean
    public RetryTemplate retryTemplate() {
        Map<Class<? extends Throwable>, Boolean> exceptionClass = new HashMap<>();
        exceptionClass.put(RetryalbeException.class, true);

        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(2000);

        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(2, exceptionClass);
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(simpleRetryPolicy);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        return retryTemplate;
    }
}
