package me.practice.kotlinbatch.items.simple.job

import org.junit.jupiter.api.Assertions.*
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

/**
 * Created by LYT to 2021/07/27
 */
@SpringBatchTest
@SpringBootTest(
    classes = [
        SimpleJobConfiguration::class,
        MileageSaveStepListener::class,
        MileageSaveJobListener::class,
        MileageSaveWriter::class,
        P6spyLogMessageFormatConfiguration::class,
        TestBatchConfig::class
    ],
    properties = ["${BatchItem.JOB_NAME_CONFIG}=${BatchItem.CHANGE_PRODUCT_SHIPPING_COMPLETE}_JOB"]
)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class SimpleJobConfigurationTest