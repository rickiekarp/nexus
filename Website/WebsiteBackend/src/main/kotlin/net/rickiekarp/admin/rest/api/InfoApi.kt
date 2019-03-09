package net.rickiekarp.admin.rest.api

import net.rickiekarp.foundation.config.ServerContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api")
class InfoApi {

    @RequestMapping("info/version")
    @GetMapping()
    fun infoVersion(): ResponseEntity<String> {
        return ResponseEntity(ServerContext.serverVersion, HttpStatus.OK)
    }
}
