package io.springbatch.springbatchlecture.config;

import io.springbatch.springbatchlecture.*;
import io.springbatch.springbatchlecture.validator.CustomJobParameterValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobInstanceConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ExecutionContextTasklet1 executionContextTasklet1;
    private final ExecutionContextTasklet2 executionContextTasklet2;
    private final ExecutionContextTasklet3 executionContextTasklet3;
    private final ExecutionContextTasklet4 executionContextTasklet4;
    private final JobExecutionListener jobRepositoryListener;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job1")
                .start(step1())
                .next(step2())
//                .next(step3())
//                .next(step4())
//                .listener(jobRepositoryListener)
//                .validator(new CustomJobParameterValidator())
//                .validator(new DefaultJobParametersValidator(new String[]{"name", "date"}, new String[]{"count"}))
//                .preventRestart()
//                .incrementer(new CustomJobParametersIncrementer())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        return RepeatStatus.FINISHED;
                    }
                })
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        throw new RuntimeException("aa");
//                        return null;
                    }
                })
                .startLimit(3)
                .build();
    }

}
