package net.rickiekarp.loginserver.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.env.Environment
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "loginEntityManager",
        transactionManagerRef = "loginTransactionManager",
        basePackages = ["net.rickiekarp.loginserver.config"]
)
open class LoginDatabaseConfig {

    @Autowired
    private val env: Environment? = null

    /**
     * DataSource definition for database connection. Settings are read from
     * the application.yml file (using the env object).
     */
    @Primary
    @Bean(name = ["dataSource"])
    open fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName(env!!.getProperty("db.driver")!!)
        dataSource.url = env.getProperty("db.url")
        dataSource.username = env.getProperty("db.username")
        dataSource.password = env.getProperty("db.password")
        return dataSource
    }

    @Primary
    @Bean(name = ["loginEntityManager"])
    open fun entityManagerFactory(builder: EntityManagerFactoryBuilder, @Qualifier("dataSource") dataSource: DataSource): LocalContainerEntityManagerFactoryBean {

        //TODO: remove additional property once https://hibernate.atlassian.net/browse/HHH-12368 is resolved
        val additionalProperties = HashMap<String, Any>()
        additionalProperties.put("hibernate.jdbc.lob.non_contextual_creation", true)

        return builder
                .dataSource(dataSource)
                .packages("net.rickiekarp.loginserver.database")
                .persistenceUnit("applicationPU")
                .properties(additionalProperties)
                .build()
    }

    @Primary
    @Bean(name = ["loginTransactionManager"])
    open fun transactionManager(@Qualifier("loginEntityManager") entityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }

    /**
     * PersistenceExceptionTranslationPostProcessor is a bean post processor
     * which adds an advisor to any bean annotated with Repository so that any
     * platform-specific exceptions are caught and then rethrown as one
     * Spring's unchecked data access exceptions (i.e. a subclass of
     * DataAccessException).
     */
    @Primary
    @Bean(name = ["loginExceptionTranslation"])
    open fun exceptionTranslation(): PersistenceExceptionTranslationPostProcessor {
        return PersistenceExceptionTranslationPostProcessor()
    }
}