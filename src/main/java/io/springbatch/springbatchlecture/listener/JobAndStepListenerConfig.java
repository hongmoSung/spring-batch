package io.springbatch.springbatchlecture.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobAndStepListenerConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CustomStepExecutionListener customStepExecutionListener;

    @Bean
    public Job jobAndStepListenerJob() {
        return jobBuilderFactory.get("jobAndStepListenerJob")
                .incrementer(new RunIdIncrementer())
                .start(listenerStep1())
                .next(listenerStep2())
//                .listener(new CustomJobExecutionListener())
                .listener(new CustomAnnotationJobExecutionListener())
                .build();
    }

    @Bean
    public Step listenerStep1() {
        return stepBuilderFactory.get("listenerStep1")
                .tasklet((contribution, chunkContext) -> RepeatStatus.FINISHED)
                .listener(customStepExecutionListener)
                .build();
    }

    @Bean
    public Step listenerStep2() {
        return stepBuilderFactory.get("listenerStep2")
                .tasklet((contribution, chunkContext) -> RepeatStatus.FINISHED)
                .listener(customStepExecutionListener)
                .build();
    }

}
