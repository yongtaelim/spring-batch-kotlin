package me.practice.kotlinbatch.person

import javax.persistence.*

@Entity
data class Person (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,

    var name: String,
    var address: String
) {
    override fun toString() = "id: $id name: $name address: $address"
}