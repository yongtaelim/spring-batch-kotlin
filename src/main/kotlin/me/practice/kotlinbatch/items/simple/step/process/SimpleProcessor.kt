package me.practice.kotlinbatch.items.simple.step.process

import me.practice.kotlinbatch.common.domain.entity.Person
import org.springframework.batch.item.ItemProcessor
import org.springframework.context.annotation.Configuration

@Configuration
class SimpleProcessor: ItemProcessor<Person, Person> {
    override fun process(person: Person): Person? {
        println("simple batch process start.")

        // logic...
        if (person.id == 2 || person.id == 3)
            return null

        // test case - RollBack
//        if (person.id == 4) {
//            throw RuntimeException("increase skip count")
//        }

        println("simple batch process finished.")

        return person
    }
}