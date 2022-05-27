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
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

//@Configuration
@RequiredArgsConstructor
public class SkipListenerConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job skipListenerJob() {
        return jobBuilderFactory.get("skipListenerJob")
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
                .processor((ItemProcessor<? super Integer, ? extends String>) item -> {
                    if (item == 4) {
                        throw new CustomSkipException("process skipped");
                    }
                    return "item" + item;
                })
                .writer(items -> {
                    for (String item : items) {
                        if (item.equals("5")) {
                            throw new CustomSkipException("write skipped");
                        }
                        System.out.println("item = " + item);
                    }
                })
                .faultTolerant()
                .skip(CustomSkipException.class)
                .skipLimit(2)
                .listener(new CustomSkipListener())
                .build();
    }

    @Bean
    public ItemReader<Integer> listItemReader() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        return new ListItemReader<>(list);
    }
}
