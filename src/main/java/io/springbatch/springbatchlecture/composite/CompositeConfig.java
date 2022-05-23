package io.springbatch.springbatchlecture.composite;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

//@Configuration
@RequiredArgsConstructor
public class CompositeConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job compositeJob() {
        return jobBuilderFactory.get("compositeJob")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("compositeStep1")
                .<String, String>chunk(10)
                .reader(new ItemReader<>() {
                    int i = 0;
                    @Override
                    public String read() {
                        i++;
                        return i > 10 ? null : "item";
                    }
                })
                .processor(customItemProcessor())
                .writer(items -> System.out.println("items = " + items))
                .build();
    }

    @Bean
    public ItemProcessor<? super String, String> customItemProcessor() {
        List itemProcessor = new ArrayList();
        itemProcessor.add(new CustomCompositeItemProcessor());
        itemProcessor.add(new CustomCompositeItemProcessor2());
        return new CompositeItemProcessorBuilder<>()
                .delegates(itemProcessor)
                .build();
    }
}
