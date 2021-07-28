package me.practice.kotlinbatch.common.config

import me.practice.kotlinbatch.querydsl.config.QuerydslConfiguration
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * Created by LYT to 2021/07/27
 */
@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@EntityScan("me.practice.kotlinbatch.common.domain.entity")
@EnableJpaRepositories("me.practice.kotlinbatch.common.repository")
@Import(QuerydslConfiguration::class)
class TestBatchConfig {
}