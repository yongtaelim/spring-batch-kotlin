package me.practice.kotlinbatch.items.simple.step.write

import me.practice.kotlinbatch.common.domain.entity.Person
import me.practice.kotlinbatch.common.repository.PersonRepository
import org.apache.logging.log4j.LogManager
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Configuration

@Configuration
class SimpleWriter(
    val personRepository: PersonRepository
): ItemWriter<Person> {
    val log = LogManager.getLogger()

    override fun write(people: MutableList<out Person>) {
        log.info("simple batch writer start.")

        for (person in people) {
            person.address = "1"
            println(person.toString())
        }

        personRepository.saveAll(people)

        log.info("simple batch writer finished.")
    }
}