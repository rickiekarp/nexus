package net.rickiekarp.admin

import net.rickiekarp.foundation.config.Application
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(value = ["net.rickiekarp.foundation", "net.rickiekarp.admin"])
open class WebsiteApplication: SpringBootServletInitializer() {

    override fun configure(springApplicationBuilder: SpringApplicationBuilder): SpringApplicationBuilder {
        return springApplicationBuilder
                .sources(WebsiteApplication::class.java)
                .properties(Application().getProperties(WebsiteApplication::class.java, "Website"))
    }
}

fun main(args: Array<String>) {
    SpringApplicationBuilder(WebsiteApplication::class.java)
            .sources(WebsiteApplication::class.java)
            .properties(Application().getProperties(WebsiteApplication::class.java, "Website"))
            .run(*args)
}