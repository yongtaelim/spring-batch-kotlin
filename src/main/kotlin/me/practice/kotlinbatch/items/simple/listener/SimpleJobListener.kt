package me.practice.kotlinbatch.items.simple.listener

import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.listener.JobExecutionListenerSupport
import org.springframework.stereotype.Component

@Component
class SimpleJobListener: JobExecutionListenerSupport() {

    override fun beforeJob(jobExecution: JobExecution) {
        super.beforeJob(jobExecution)
    }

    override fun afterJob(jobExecution: JobExecution) {
        super.afterJob(jobExecution)
    }

}