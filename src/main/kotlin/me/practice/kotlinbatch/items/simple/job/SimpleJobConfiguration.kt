package me.practice.kotlinbatch.items.simple.job

import me.practice.kotlinbatch.items.simple.enums.BatchItem
import me.practice.kotlinbatch.items.simple.step.write.SimpleWriter
import me.practice.kotlinbatch.person.Person
import me.practice.kotlinbatch.person.PersonRepository
import me.practice.kotlinbatch.querydsl.QuerydslPagingItemReader
import me.practice.kotlinbatch.items.simple.listener.SimpleStepListener
import me.practice.kotlinbatch.items.simple.step.process.SimpleProcessor
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import javax.persistence.EntityManagerFactory

@Configuration
@EnableBatchProcessing
@EnableScheduling
class SimpleJobConfiguration(
    @Autowired val jobBuilderFactory: JobBuilderFactory,
    @Autowired val stepBuilderFactory: StepBuilderFactory,
    @Autowired val entityManagerFactory: EntityManagerFactory,
    @Autowired val personRepository: PersonRepository,
    @Autowired val simpleStepListener: SimpleStepListener,
    @Autowired val simpleProcessor: SimpleProcessor,
    @Autowired val simpleWriter: SimpleWriter
) {
    private val chunkSize = 10
    private val pageSize = 10

    @Bean
    fun simpleJob() =
        jobBuilderFactory.get(BatchItem.SIMPLE_JOB.jobName)
            .start(simpleStep())
            .build()

    @Bean
    fun simpleStep() =
        stepBuilderFactory.get(BatchItem.SIMPLE_STEP.jobName)
            .chunk<Person, Person>(chunkSize)
            .reader(reader())
            .processor(simpleProcessor)
            .writer(simpleWriter)
            .listener(simpleStepListener)
            .build()

    fun reader(): QuerydslPagingItemReader<Person> =
        QuerydslPagingItemReader(entityManagerFactory, pageSize) { personRepository.findAllInBatch() }
}