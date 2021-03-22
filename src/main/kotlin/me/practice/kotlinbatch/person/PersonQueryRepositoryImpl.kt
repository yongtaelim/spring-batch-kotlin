package me.practice.kotlinbatch.person

import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import me.practice.kotlinbatch.person.QPerson.*
import org.springframework.beans.factory.annotation.Autowired

class PersonQueryRepositoryImpl(
    @Autowired val queryFactory: JPAQueryFactory
) : PersonQueryRepository {

    override fun findAllInBatch(): JPAQuery<Person> =
        queryFactory
            .selectFrom(person)
}