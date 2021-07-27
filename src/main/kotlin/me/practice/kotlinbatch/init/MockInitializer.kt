package me.practice.kotlinbatch.init

import me.practice.kotlinbatch.common.domain.entity.Person
import me.practice.kotlinbatch.common.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class MockInitializer(
    @Autowired val personRepository: PersonRepository
) {

    @PostConstruct
    fun init() {
        val people = listOf(
            Person(id = 1, name = "테스트1", address = "주소17"),
            Person(id = 2, name = "테스트12", address = "주소16"),
            Person(id = 3, name = "테스트13", address = "주소15"),
            Person(id = 4, name = "테스트14", address = "주소14"),
            Person(id = 5, name = "테스트15", address = "주소13"),
            Person(id = 6, name = "테스트16", address = "주소12"),
            Person(id = 7, name = "테스트17", address = "주소11"),
            Person(id = 8, name = "테스트18", address = "9주소1"),
            Person(id = 9, name = "테스트19", address = "8주소1"),
            Person(id = 10, name = "테스트111", address = "7주소1"),
            Person(id = 11, name = "테스트21", address = "6주소1"),
            Person(id = 12, name = "테스트31", address = "5주소1"),
            Person(id = 13, name = "테스트41", address = "4주소1"),
            Person(id = 14, name = "테스트51", address = "3주소1"),
            Person(id = 15, name = "테스트61", address = "2주소1"),
            Person(id = 16, name = "테스트71", address = "2주소1"),
            Person(id = 17, name = "테스트81", address = "1주소1")
        )
        personRepository.saveAll(people)
    }
}