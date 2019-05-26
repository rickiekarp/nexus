package net.rickiekarp.homeserver.rest.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.rickiekarp.foundation.data.dao.ApplicationSettingsDao
import net.rickiekarp.foundation.data.dto.ResultDTO
import net.rickiekarp.foundation.core.services.mail.EmailService
import net.rickiekarp.foundation.data.dto.EmailDto
import net.rickiekarp.foundation.logger.Log
import net.rickiekarp.foundation.model.NotificationTokenData
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.beans.factory.annotation.Autowired

@RestController
@RequestMapping("statistics")
class StatisticsApi {

    @Autowired
    var emailService: EmailService? = null

    @Autowired
    var settingsRepo: ApplicationSettingsDao? = null

    @PostMapping(value = ["shoppingValue"])
    fun generateShoppingStatistic(@RequestHeader(name = "X-Notification-Token") notificationToken: String, @RequestBody mail: EmailDto): ResponseEntity<ResultDTO> {
        val setting = settingsRepo!!.getApplicationSettingByIdentifier("notificationtoken")
        if (setting != null) {
            val notificationList: List<NotificationTokenData> = jacksonObjectMapper().readValue(setting.content!!)
            val notificationData = emailService!!.findNotificationData(notificationToken, notificationList)
            if (notificationData != null) {
                mail.subject = "(${notificationData.name}) ${mail.subject}" //Add notification job name to subject
                emailService!!.sendInfoMail(mail, notificationData.name!!)
                return ResponseEntity(ResultDTO("success"), HttpStatus.OK)
            } else {
                Log.DEBUG.info("Mail was not sent! Invalid notification token: $notificationToken")
            }
        }

        return ResponseEntity(ResultDTO("not_sent"), HttpStatus.OK)
    }
}
