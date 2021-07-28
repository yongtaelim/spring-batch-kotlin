package me.practice.kotlinbatch.common.support

import org.springframework.batch.core.JobParameters
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory

/**
 * Created by LYT to 2021/07/28
 */
@SpringBatchTest
open class BatchTestSupport {

    @Autowired
    protected lateinit var jobLauncherTestUtils: JobLauncherTestUtils

    @Autowired
    protected lateinit var entityManagerFactory: EntityManagerFactory

    protected val entityManager: EntityManager by lazy { entityManagerFactory.createEntityManager() }

    /**
     * 유일한 값을 포함하는 Job Parameter Builder를 얻어온다.
     * @return JobParametersBuilder
     */
    protected fun getUniqueParameterBuilder() = jobLauncherTestUtils.uniqueJobParametersBuilder

    /**
     * Job 실행
     * @return JobExecution
     */
    protected fun launchJob() = jobLauncherTestUtils.launchJob()

    /**
     * Job 실행 ( Parameter 포함 )
     * @param jobParameters JobParameters
     * @return JobExecution
     */
    protected fun launchJob(jobParameters: JobParameters) = jobLauncherTestUtils.launchJob(jobParameters)

    /**
     * Entity Save
     * @param entity Any
     * @return Any
     */
    protected fun save(entity: Any): Any {
        entityManager.persist(entity)
        return entity
    }

    /**
     * Entity Save All
     * @param entities List<Any>
     * @return List<Any>
     */
    protected fun saveAll(entities: List<Any>): List<Any> {
        entities.forEach { entity ->
            entityManager.persist(entity)
        }
        return entities
    }

    /**
     * Entity Delete
     * @param entity Any
     * @return Any
     */
    protected fun delete(entity: Any): Any {
        entityManager.remove(entity)
        return entity
    }

    /**
     * Entity Delete All
     * @param entities List<Any>
     * @return List<Any>
     */
    protected fun deleteAll(entities: List<Any>): List<Any> {
        entities.forEach { entity ->
            entityManager.remove(entity)
        }
        return entities
    }
}