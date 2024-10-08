package basePack.config;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class StepExecutionLi implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Step started: " + stepExecution.getStepName());
    }

    public ExitStatus afterStep(StepExecution stepExecution) {
        int readCount = (int) stepExecution.getReadCount();
        int writeCount = (int) stepExecution.getWriteCount();
        System.out.println("Step completed. Read Count: " + readCount + ", Write Count: " + writeCount);
        return stepExecution.getExitStatus();
    }
}
