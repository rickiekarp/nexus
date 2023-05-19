package net.rickiekarp.loginserver

import net.rickiekarp.foundation.config.Application
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@ComponentScan(value = ["net.rickiekarp"])
open class LoginServerApplication: SpringBootServletInitializer() {

    override fun configure(springApplicationBuilder: SpringApplicationBuilder): SpringApplicationBuilder {
        return springApplicationBuilder
                .sources(LoginServerApplication::class.java)
                .properties(Application().getProperties(LoginServerApplication::class.java, "LoginServer"))
    }
}

fun main(args: Array<String>) {
    SpringApplicationBuilder(LoginServerApplication::class.java)
            .sources(LoginServerApplication::class.java)
            .properties(Application().getProperties(LoginServerApplication::class.java, "LoginServer"))
            .run(*args)
}