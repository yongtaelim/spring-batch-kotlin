package me.practice.kotlinbatch.common.repository.custom

import com.querydsl.jpa.impl.JPAQuery
import me.practice.kotlinbatch.common.domain.entity.Person

interface PersonQueryRepository {
    fun findAllInBatch(): JPAQuery<Person>
}