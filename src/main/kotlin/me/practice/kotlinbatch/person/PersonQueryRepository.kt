package me.practice.kotlinbatch.person

import com.querydsl.jpa.impl.JPAQuery

interface PersonQueryRepository {
    fun findAllInBatch(): JPAQuery<Person>
}