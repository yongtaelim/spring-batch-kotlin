package me.practice.kotlinbatch.querydsl.config

import com.p6spy.engine.logging.P6LogOptions
import com.p6spy.engine.spy.P6SpyOptions
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class P6spyLogMessageFormatConfiguration {
    companion object {
        private const val BATCH_TABLE_NAME_PREFIX = "BATCH"
    }

    @PostConstruct
    fun setLogMessageFormat() {
        // apply pretty format
        P6SpyOptions.getActiveInstance().logMessageFormat = P6spySqlFormatConfiguration::class.java.name

        // apply batch exclude filter
        val activeInstance = P6LogOptions.getActiveInstance()
        activeInstance.filter = true
        activeInstance.exclude = BATCH_TABLE_NAME_PREFIX
    }
}