package io.springbatch.springbatchlecture.config;

import io.springbatch.springbatchlecture.CustomStepListener;
import io.springbatch.springbatchlecture.JobListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
//@Configuration
@RequiredArgsConstructor
public class ScopeConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("scopeJob")
                .start(step1(null))
                .next(step2())
                .listener(new JobListener())
                .build();
    }

    @Bean
    @JobScope
    public Step step1(@Value("#{jobParameters['message']}") String message) {
        log.info("step1 was executed message -> {}", message);
        return stepBuilderFactory.get("step1")
                .tasklet(tasklet1(null))
                .build();
    }

    @Bean
    public Step step2() {
        log.info("step2 was executed");
        return stepBuilderFactory.get("step2")
                .tasklet(tasklet2(null))
                .listener(new CustomStepListener())
                .build();
    }

    @Bean
    @StepScope
    public Tasklet tasklet1(@Value("#{jobExecutionContext['name']}") String name) {
        log.info("tasklet1 was executed name -> {}", name);
        return ((contribution, chunkContext) -> RepeatStatus.FINISHED);
    }

    @Bean
    @StepScope
    public Tasklet tasklet2(@Value("#{stepExecutionContext['name2']}") String name2) {
        log.info("tasklet2 was executed name -> {}", name2);
        return ((contribution, chunkContext) -> RepeatStatus.FINISHED);
    }

}
