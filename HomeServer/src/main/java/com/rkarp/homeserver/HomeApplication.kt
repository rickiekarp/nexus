package com.rkarp.homeserver

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(value = ["com.rkarp.foundation", "com.rkarp.homeserver"])
open class HomeApplication { }

fun main(args: Array<String>) {
    SpringApplication.run(HomeApplication::class.java, *args)
}

class ApplicationServletInitializer : SpringBootServletInitializer() {
    override fun configure(builder: SpringApplicationBuilder): SpringApplicationBuilder {
        return builder.sources(HomeApplication::class.java)
    }
}
