package me.practice.kotlinbatch

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.system.exitProcess

@SpringBootApplication
class KotlinBatchApplication

fun main(args: Array<String>) {
    val applicationContext = runApplication<KotlinBatchApplication>(*args)

    // kill process when finished batch
    exitProcess(SpringApplication.exit(applicationContext))
}