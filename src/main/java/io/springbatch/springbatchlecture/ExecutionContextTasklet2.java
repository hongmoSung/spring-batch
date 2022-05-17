package io.springbatch.springbatchlecture;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExecutionContextTasklet2 implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws InterruptedException {
        log.info("step2 was executed");
//        throw new RuntimeException("step2 was failed");
//        Thread.sleep(3000);
//        ExecutionContext jobExecutionContext = contribution.getStepExecution().getJobExecution().getExecutionContext();
//        ExecutionContext stepExecutionContext = contribution.getStepExecution().getExecutionContext();
//
//        String jobName = chunkContext.getStepContext().getStepExecution().getJobExecution().getJobInstance().getJobName();
//        String stepName = chunkContext.getStepContext().getStepExecution().getStepName();
//
//        log.info("jobName = {}", jobExecutionContext.get("jobName"));
//        log.info("stepName = {}", stepExecutionContext.get("stepName"));
//
//        if (jobExecutionContext.get("jobName") != null) jobExecutionContext.put("jobName", jobName);
//        if (stepExecutionContext.get("stepName") != null) stepExecutionContext.put("stepName", stepName);

        return RepeatStatus.FINISHED;
    }
}
