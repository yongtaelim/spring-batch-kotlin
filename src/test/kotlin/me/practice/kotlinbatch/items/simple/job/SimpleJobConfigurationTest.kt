package me.practice.kotlinbatch.items.simple.job

import me.practice.kotlinbatch.common.TestBatchConfig
import me.practice.kotlinbatch.common.domain.constant.BatchItem
import me.practice.kotlinbatch.common.domain.entity.Person
import me.practice.kotlinbatch.items.simple.listener.SimpleStepListener
import me.practice.kotlinbatch.items.simple.step.process.SimpleProcessor
import me.practice.kotlinbatch.items.simple.step.write.SimpleWriter
import me.practice.kotlinbatch.querydsl.config.P6spyLogMessageFormatConfiguration
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory

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
    ]
)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class SimpleJobConfigurationTest(
    val jobLauncherTestUtils: JobLauncherTestUtils,
    val entityManagerFactory: EntityManagerFactory
) {

    lateinit var people: List<Person>

    val entityManager: EntityManager by lazy { entityManagerFactory.createEntityManager() }

    @BeforeEach
    fun before() {
        people = (0..20).map {
            Person(name = "사람$it", address = "주소$it")
        }

        val transaction = entityManager.transaction
        transaction.begin()
        people.forEach { person ->
            entityManager.persist(person)
        }
        transaction.commit()
    }

    @AfterEach
    fun after() {
        val transaction = entityManager.transaction
        transaction.begin()
        people.forEach { person ->
            entityManager.remove(person)
        }
        transaction.commit()
        entityManager.clear()
    }

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