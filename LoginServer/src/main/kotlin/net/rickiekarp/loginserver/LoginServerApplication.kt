package net.rickiekarp.loginserver

import net.rickiekarp.foundation.config.Application
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(value = ["net.rickiekarp.foundation", "net.rickiekarp.loginserver"])
open class LoginServerApplication: SpringBootServletInitializer() {

    override fun configure(springApplicationBuilder: SpringApplicationBuilder): SpringApplicationBuilder {
        return springApplicationBuilder
                .sources(LoginServerApplication::class.java)
                .properties(Application().getProperties(LoginServerApplication::class.java))
    }
}

fun main(args: Array<String>) {
    SpringApplicationBuilder(LoginServerApplication::class.java)
            .sources(LoginServerApplication::class.java)
            .properties(Application().getProperties(LoginServerApplication::class.java))
            .run(*args)
}