package net.rickiekarp.loginserver.config

import io.lettuce.core.ClientOptions
import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.env.Environment
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "loginEntityManager",
        transactionManagerRef = "loginTransactionManager",
        basePackages = ["net.rickiekarp.loginserver.config"]
)
@EnableRedisRepositories(basePackages = ["net.rickiekarp.foundation.config.redis"])
open class ApplicationConfig {

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

        return builder
                .dataSource(dataSource)
                .packages("net.rickiekarp.loginserver.database")
                .persistenceUnit("loginPU")
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

    @Bean
    open fun clientOptions(): ClientOptions? {
        return ClientOptions.builder()
            .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
            .autoReconnect(true)
            .build()
    }

    @Bean open fun redisConnectionFactory(): RedisConnectionFactory?{
        val config = RedisStandaloneConfiguration()
        config.hostName = env!!.getProperty("redis.url")!!
        config.port = env.getProperty("redis.port")!!.toInt()
        val clientConfig: LettuceClientConfiguration = LettuceClientConfiguration.builder()
            .commandTimeout(java.time.Duration.ofMillis(10000))
            .build()
        return LettuceConnectionFactory(config, clientConfig)
    }

    @Bean open fun stringRedisTemplate(
        @Qualifier("redisConnectionFactory") redisConnectionFactory: RedisConnectionFactory?
    ): StringRedisTemplate?{
        val template = StringRedisTemplate()
        template.connectionFactory = redisConnectionFactory
        return template
    }

    @Bean open fun messagePackRedisTemplate(
        @Qualifier("redisConnectionFactory") redisConnectionFactory: RedisConnectionFactory?
    ): RedisTemplate<String, ByteArray>?{
        val template: RedisTemplate<String, ByteArray> = RedisTemplate<String, ByteArray>()
        template.connectionFactory = redisConnectionFactory
        template.keySerializer = StringRedisSerializer()
        template.isEnableDefaultSerializer = false
        return template
    }
}