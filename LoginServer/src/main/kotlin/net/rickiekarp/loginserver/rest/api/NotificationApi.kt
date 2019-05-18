package net.rickiekarp.loginserver.rest.api

import net.rickiekarp.foundation.dto.exception.ResultDTO
import net.rickiekarp.loginserver.config.EmailServiceImpl
import net.rickiekarp.loginserver.dto.EmailDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage

@RestController
@RequestMapping("notify")
class NotificationApi {

    @Autowired
    var template: SimpleMailMessage? = null

    @Autowired
    var service: EmailServiceImpl? = null

    @PostMapping(value = ["send"])
    fun sendMailContent(@RequestBody mail: EmailDto): ResponseEntity<ResultDTO> {
        service!!.sendMail(mail)
        return ResponseEntity(ResultDTO("success"), HttpStatus.OK)
    }

    @PostMapping(value = ["sendStatus"])
    fun sendMailByStatus(@RequestHeader(name = "X-Notification") notificationType: Int): ResponseEntity<ResultDTO> {
        service!!.sendMailByType(notificationType)
        return ResponseEntity(ResultDTO("success"), HttpStatus.OK)
    }
}
