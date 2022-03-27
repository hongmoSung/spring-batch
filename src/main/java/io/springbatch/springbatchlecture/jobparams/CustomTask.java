package io.springbatch.springbatchlecture.jobparams;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.Map;

@Slf4j
public class CustomTask implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("step1 start!");
        JobParameters jobParameters = contribution.getStepExecution().getJobExecution().getJobParameters();
        Map<String, Object> jobParameters1 = chunkContext.getStepContext().getJobParameters();
        for (String s : jobParameters1.keySet()) {
            log.info(s);
        }
//                    throw new RuntimeException("step1 has failed");
        return RepeatStatus.FINISHED;
    }
}
