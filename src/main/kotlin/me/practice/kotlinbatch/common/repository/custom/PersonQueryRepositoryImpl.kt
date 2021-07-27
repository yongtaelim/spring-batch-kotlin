package me.practice.kotlinbatch.common.repository.custom

import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import me.practice.kotlinbatch.common.domain.entity.Person
import me.practice.kotlinbatch.common.domain.entity.QPerson
import me.practice.kotlinbatch.common.domain.entity.QPerson.*
import org.springframework.beans.factory.annotation.Autowired

class PersonQueryRepositoryImpl(
    @Autowired val queryFactory: JPAQueryFactory
) : PersonQueryRepository {

    override fun findAllInBatch(): JPAQuery<Person> =
        queryFactory
            .selectFrom(person)
}