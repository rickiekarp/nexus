package net.rickiekarp.loginserver.rest.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController {

    @RequestMapping(
            value = ["info"],
            method = arrayOf(RequestMethod.GET)
    )
    fun validateProperties(): ResponseEntity<String> {
        return ResponseEntity("IT WORKS", HttpStatus.OK)
    }
}