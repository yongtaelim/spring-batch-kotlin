package me.practice.kotlinbatch.items.simple.listener

import org.slf4j.LoggerFactory
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.listener.StepExecutionListenerSupport
import org.springframework.stereotype.Component

@Component
class SimpleStepListener: StepExecutionListenerSupport() {

    private val log = LoggerFactory.getLogger(SimpleStepListener::class.java)

    override fun beforeStep(stepExecution: StepExecution) {
        val name = stepExecution.stepName
        log.info("before beforeStep. name: $name")

        super.beforeStep(stepExecution)
    }

    override fun afterStep(stepExecution: StepExecution): ExitStatus? {
        log.info(
                "\n    ⊙ after simpleStep\n" +
                        "    ├─ Name: ${stepExecution.stepName}\n" +
                        "    ├─ Read Count: ${stepExecution.readCount} \n" +
                        "    ├─ Write Count: ${stepExecution.writeCount}\n" +
                        "    ├─ Commit Count: ${stepExecution.commitCount}\n" +
                        "    ├─ Rollback Count: ${stepExecution.rollbackCount}\n" +
                        "    ├─ status: ${stepExecution.status}"
        )

        return super.afterStep(stepExecution)
    }
}