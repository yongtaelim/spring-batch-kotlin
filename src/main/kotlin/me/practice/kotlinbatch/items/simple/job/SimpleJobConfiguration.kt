package me.practice.kotlinbatch.items.simple.job

import me.practice.kotlinbatch.common.domain.constant.BatchItem
import me.practice.kotlinbatch.items.simple.step.write.SimpleWriter
import me.practice.kotlinbatch.common.domain.entity.Person
import me.practice.kotlinbatch.common.repository.PersonRepository
import me.practice.kotlinbatch.common.querydsl.reader.QuerydslPagingItemReader
import me.practice.kotlinbatch.items.simple.listener.SimpleStepListener
import me.practice.kotlinbatch.items.simple.step.process.SimpleProcessor
import org.apache.logging.log4j.LogManager
import org.springframework.batch.core.configuration.annotation.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManagerFactory

@Configuration
class SimpleJobConfiguration(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val entityManagerFactory: EntityManagerFactory,
    val personRepository: PersonRepository,
    val simpleStepListener: SimpleStepListener,
    val simpleProcessor: SimpleProcessor,
    val simpleWriter: SimpleWriter
) {

    val log = LogManager.getLogger()

    @Bean("${BatchItem.SIMPLE}_JOB")
    fun simpleJob() =
        jobBuilderFactory.get("${BatchItem.SIMPLE}_JOB")
            .start(simpleStep(null))
            .build()

    @Bean("${BatchItem.SIMPLE}_STEP")
    @JobScope
    fun simpleStep(@Value("#{jobParameters[chunkSize]}") chunkSize: Int?) =
        stepBuilderFactory.get("${BatchItem.SIMPLE}_STEP")
            .chunk<Person, Person>(chunkSize!!)
            .reader(reader(null))
            .processor(simpleProcessor)
            .writer(simpleWriter)
            .listener(simpleStepListener)
            .build()

    @Bean("${BatchItem.SIMPLE}_READER")
    @StepScope
    fun reader(@Value("#{jobParameters[pageSize]}") pageSize: Int?): QuerydslPagingItemReader<Person> {
        val reader = QuerydslPagingItemReader(entityManagerFactory) { personRepository.findAllInBatch() }
        reader.pageSize = pageSize!!
        reader.pageOffset = false
        log.error("[Test] Batch Error....!")
        return reader
    }

}