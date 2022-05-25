package io.springbatch.springbatchlecture.parallel;

import io.springbatch.springbatchlecture.async.StopWatchJobListener;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

//@Configuration
@RequiredArgsConstructor
public class ParallelStepConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job parallelJob() {
        return jobBuilderFactory.get("parallelJob")
                .start(parallelFlow1())
                .split(taskExecutor()).add(parallelFlow2())
                .end()
                .listener(new StopWatchJobListener())
                .build();
    }

    @Bean
    public Flow parallelFlow1() {
        TaskletStep parallelStep1 = stepBuilderFactory.get("parallelStep1")
                .tasklet(tasklet()).build();
        return new FlowBuilder<Flow>("parallelFlow1")
                .start(parallelStep1)
                .build();
    }

    @Bean
    public Flow parallelFlow2() {
        TaskletStep parallelStep2 = stepBuilderFactory.get("parallelStep2")
                .tasklet(tasklet()).build();

        TaskletStep parallelStep3 = stepBuilderFactory.get("parallelStep3")
                .tasklet(tasklet()).build();
        return new FlowBuilder<Flow>("parallelFlow")
                .start(parallelStep2)
                .next(parallelStep3)
                .build();
    }

    @Bean
    public Tasklet tasklet() {
        return new ParallelTasklet();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setMaxPoolSize(8);
        taskExecutor.setThreadNamePrefix("async-thread");
        return taskExecutor;
    }

}
