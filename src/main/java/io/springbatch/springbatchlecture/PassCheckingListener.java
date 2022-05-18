package io.springbatch.springbatchlecture;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

@Slf4j
public class PassCheckingListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("before step");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        String exitCode = stepExecution.getExitStatus().getExitCode();

        if (!exitCode.equals(ExitStatus.FAILED.getExitCode())) {
            return new ExitStatus("PASS");
        }
        return null;
    }
}
