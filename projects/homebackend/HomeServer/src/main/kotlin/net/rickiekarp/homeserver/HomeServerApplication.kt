package net.rickiekarp.homeserver

import net.rickiekarp.foundation.config.Application
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(value = ["net.rickiekarp"])
open class HomeServerApplication: SpringBootServletInitializer() {

    override fun configure(springApplicationBuilder: SpringApplicationBuilder): SpringApplicationBuilder {
        return springApplicationBuilder
                .sources(HomeServerApplication::class.java)
                .properties(Application().getProperties(HomeServerApplication::class.java, "HomeServer"))
    }
}

fun main(args: Array<String>) {
    SpringApplicationBuilder(HomeServerApplication::class.java)
            .sources(HomeServerApplication::class.java)
            .properties(Application().getProperties(HomeServerApplication::class.java, "HomeServer"))
            .run(*args)
}