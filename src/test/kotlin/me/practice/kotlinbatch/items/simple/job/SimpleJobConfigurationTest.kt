package me.practice.kotlinbatch.items.simple.job

import me.practice.kotlinbatch.common.TestBatchConfig
import me.practice.kotlinbatch.common.domain.constant.BatchItem
import me.practice.kotlinbatch.items.simple.listener.SimpleStepListener
import me.practice.kotlinbatch.items.simple.step.process.SimpleProcessor
import me.practice.kotlinbatch.items.simple.step.write.SimpleWriter
import me.practice.kotlinbatch.querydsl.config.P6spyLogMessageFormatConfiguration
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

/**
 * Created by LYT to 2021/07/27
 */
@SpringBatchTest
@SpringBootTest(
    classes = [
        SimpleJobConfiguration::class,
        SimpleStepListener::class,
        SimpleProcessor::class,
        SimpleWriter::class,
        P6spyLogMessageFormatConfiguration::class,
        TestBatchConfig::class
    ],
    properties = ["${BatchItem.JOB_NAME_CONFIG}=${BatchItem.SIMPLE}_JOB"]
)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class SimpleJobConfigurationTest(
    val jobLauncherTestUtils: JobLauncherTestUtils
) {

    @Test
    fun `test job run test`() {
        // Given
        val jobParameters = jobLauncherTestUtils
            .uniqueJobParametersBuilder
            .addLong("chunkSize", 5L)
            .addLong("pageSize", 20L)
            .toJobParameters()

        // When
        val jobExecution = jobLauncherTestUtils.launchJob(jobParameters)

        // Then
        Assertions.assertThat(jobExecution.status).isEqualTo(BatchStatus.COMPLETED)

    }

}