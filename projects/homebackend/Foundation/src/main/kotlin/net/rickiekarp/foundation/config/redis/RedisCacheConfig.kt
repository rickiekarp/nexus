package net.rickiekarp.foundation.config.redis

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory

class RedisCacheConfig {

    @Autowired
    private val env: Environment? = null

    @Bean
    internal fun jedisConnectionFactory(): JedisConnectionFactory {
        val config = RedisStandaloneConfiguration()
        config.hostName = env!!.getProperty("redis.url")!!
        config.port = env.getProperty("redis.port")!!.toInt()
        return JedisConnectionFactory(config)
    }
}
