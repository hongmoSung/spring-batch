package io.springbatch.springbatchlecture.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RetryListenerConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job retryJob() {
        return jobBuilderFactory.get("retryJob")
                .incrementer(new RunIdIncrementer())
                .start(retryStep())
                .build();
    }

    @Bean
    public Step retryStep() {
        return stepBuilderFactory.get("retryStep")
                .<Integer, String>chunk(10)
                .reader(listItemReader())
                .processor(new RetryItemProcessor())
                .writer(new RetryItemWriter())
                .faultTolerant()
                .retry(CustomRetryException.class)
                .retryLimit(2)
                .listener(new CustomRetryListener())
                .build();
    }

    @Bean
    public ItemReader<Integer> listItemReader() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        return new LinkedListItemReader2<>(list);
    }

}
