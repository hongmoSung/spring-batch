package io.springbatch.springbatchlecture.itemstream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
//@Configuration
@RequiredArgsConstructor
public class ItemStreamConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("streamJob")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("streamStep1")
                .chunk(5)
                .reader(itemReader())
                .writer((ItemWriter<? super Object>) itemWriter())
                .build();
    }

    @Bean
    public ItemWriter<? super String> itemWriter() {
        return new CustomItemStreamWriter();
    }

    public CustomItemStreamReader itemReader() {
        List<String> items = new ArrayList<>(10);

        for (int i = 0; i <= 10; i++) {
            items.add(String.valueOf(i));
        }

        return new CustomItemStreamReader(items);

    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("streamStep2")
                .tasklet((contribution, chunkContext) -> RepeatStatus.FINISHED)
                .build();
    }

}
