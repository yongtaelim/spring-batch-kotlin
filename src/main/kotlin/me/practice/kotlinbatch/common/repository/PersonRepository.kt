package me.practice.kotlinbatch.common.repository

import me.practice.kotlinbatch.common.domain.entity.Person
import me.practice.kotlinbatch.common.repository.custom.PersonQueryRepository
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository: JpaRepository<Person, Int>, PersonQueryRepository {
}