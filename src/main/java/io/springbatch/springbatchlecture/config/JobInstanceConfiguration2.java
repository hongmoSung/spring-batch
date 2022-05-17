package io.springbatch.springbatchlecture.config;

import io.springbatch.springbatchlecture.ExecutionContextTasklet1;
import io.springbatch.springbatchlecture.ExecutionContextTasklet2;
import io.springbatch.springbatchlecture.ExecutionContextTasklet3;
import io.springbatch.springbatchlecture.ExecutionContextTasklet4;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobInstanceConfiguration2 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ExecutionContextTasklet1 executionContextTasklet1;
    private final ExecutionContextTasklet2 executionContextTasklet2;
    private final ExecutionContextTasklet3 executionContextTasklet3;
    private final ExecutionContextTasklet4 executionContextTasklet4;
    private final JobExecutionListener jobRepositoryListener;

    @Bean
    public Job job2() {
        return jobBuilderFactory.get("job2")
                .start(step3())
//                .start(step1())
//                .next(step2())
//                .next(step3())
                .next(step4())
                .listener(jobRepositoryListener)
                .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet(executionContextTasklet3)
                .build();
    }

    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step4")
                .tasklet(executionContextTasklet4)
                .build();
    }

}