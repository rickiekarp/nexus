package net.rickiekarp.loginserver.rest.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StatusApi {

    @RequestMapping("status")
    @GetMapping()
    fun status(): ResponseEntity<String> {
        return ResponseEntity("test", HttpStatus.OK)
    }
}
