package me.practice.kotlinbatch.querydsl.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Configuration
class QuerydslConfiguration(@PersistenceContext val entityManager: EntityManager) {

    @Bean
    fun jpaQueryFactory() : JPAQueryFactory = JPAQueryFactory(entityManager)
}