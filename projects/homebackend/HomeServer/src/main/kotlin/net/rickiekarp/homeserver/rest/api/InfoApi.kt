package net.rickiekarp.homeserver.rest.api

import net.rickiekarp.foundation.config.ServerContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("info")
class InfoApi {

    @GetMapping("ip")
    fun getIp(req: HttpServletRequest): ResponseEntity<String> {
        val remoteAddr = req.remoteAddr
        val remotePort = req.remotePort
        val remoteIp = "$remoteAddr:$remotePort"
        return ResponseEntity(remoteIp, HttpStatus.OK)
    }

    @GetMapping("version")
    fun infoVersion(): ResponseEntity<String> {
        return ResponseEntity(ServerContext.serverVersion, HttpStatus.OK)
    }
}
