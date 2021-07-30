package me.practice.kotlinbatch.items.simple.job

import me.practice.kotlinbatch.common.config.TestBatchConfig
import me.practice.kotlinbatch.common.domain.entity.Person
import me.practice.kotlinbatch.common.support.BatchTestSupport
import me.practice.kotlinbatch.items.simple.listener.SimpleStepListener
import me.practice.kotlinbatch.items.simple.step.process.SimpleProcessor
import me.practice.kotlinbatch.items.simple.step.write.SimpleWriter
import me.practice.kotlinbatch.common.querydsl.config.P6spyLogMessageFormatConfiguration
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.boot.test.context.SpringBootTest

/**
 * Created by LYT to 2021/07/27
 */
@SpringBootTest(
    classes = [
        SimpleJobConfiguration::class,
        SimpleStepListener::class,
        SimpleProcessor::class,
        SimpleWriter::class,
        P6spyLogMessageFormatConfiguration::class,
        TestBatchConfig::class
    ]
)
internal class SimpleJobConfigurationTest: BatchTestSupport() {

    lateinit var people: List<Person>

    @BeforeEach
    fun before() {
        people = (0..20).map {
            Person(name = "사람$it", address = "주소$it")
        }

        val transaction = entityManager.transaction
        transaction.begin()
        saveAll(people)
        transaction.commit()
    }

    @AfterEach
    fun after() {
        val transaction = entityManager.transaction
        transaction.begin()
        deleteAll(people)
        transaction.commit()
        entityManager.clear()
    }

    @Test
    fun `test job run test`() {
        // Given
        val jobParameters = getUniqueParameterBuilder()
            .addLong("chunkSize", 5L)
            .addLong("pageSize", 20L)
            .toJobParameters()

        // When
        val jobExecution = launchJob(jobParameters)

        // Then
        Assertions.assertThat(jobExecution.status).isEqualTo(BatchStatus.COMPLETED)

    }

}