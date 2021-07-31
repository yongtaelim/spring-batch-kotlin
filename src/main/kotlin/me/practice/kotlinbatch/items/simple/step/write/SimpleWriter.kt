package me.practice.kotlinbatch.items.simple.step.write

import me.practice.kotlinbatch.common.domain.entity.Person
import org.apache.logging.log4j.LogManager
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Configuration

@Configuration
class SimpleWriter: ItemWriter<Person> {
    val log = LogManager.getLogger()

    override fun write(people: MutableList<out Person>) {
        log.info("simple batch writer start.")

        for (person in people) {
            println(person.toString())
        }

        log.info("simple batch writer finished.")
    }
}