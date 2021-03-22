package me.practice.kotlinbatch.person

import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository: JpaRepository<Person, Int>, PersonQueryRepository {
}