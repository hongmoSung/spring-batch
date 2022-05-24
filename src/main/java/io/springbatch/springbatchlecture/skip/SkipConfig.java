package io.springbatch.springbatchlecture.skip;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SkipConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job skipJob() {
        return jobBuilderFactory.get("skipJob")
                .start(skipStep())
                .build();
    }

    @Bean
    public Step skipStep() {
        return stepBuilderFactory.get("skipStep")
                .<String, String>chunk(5)
                .reader(new ItemReader<String>() {
                    int i = 0;
                    @Override
                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        i++;
                        if (i == 3) throw new SkippableException("skip");
                        return i > 20 ? null : String.valueOf(i);
                    }
                })
                .processor(itemProcessor())
                .writer(itemWriter())
                .faultTolerant()
                .skip(SkippableException.class)
                .skipLimit(4)
                .build();
    }

    @Bean
    public ItemWriter<? super String> itemWriter() {
        return new SkipItemWriter();
    }

    @Bean
    public ItemProcessor<? super String, String> itemProcessor() {
        return new SkipItemProcessor();
    }
}
