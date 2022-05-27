package io.springbatch.springbatchlecture.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ChunkListenerConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job chunkListenerJob() {
        return jobBuilderFactory.get("chunkListenerJob")
                .incrementer(new RunIdIncrementer())
                .start(chunkStep1())
                .build();
    }

    @Bean
    public Step chunkStep1() {
        return stepBuilderFactory.get("chunkStep1")
                .<Integer, String>chunk(10)
                .listener(new CustomChunkListener())
                .listener(new CustomItemReadListener())
                .listener(new CustomItemProcessListener())
                .listener(new CustomItemWriterListener())
                .reader(listItemReader())
                .processor((ItemProcessor<? super Integer, ? extends String>) item -> "item" + item)
                .writer(items -> {
                    System.out.println("items = " + items);
                })
                .build();
    }

    @Bean
    public ItemReader<Integer> listItemReader() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        return new ListItemReader<>(list);
    }
}
