package net.rickiekarp.loginserver.rest.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.rickiekarp.foundation.data.dao.ApplicationSettingsDao
import net.rickiekarp.foundation.dto.exception.ResultDTO
import net.rickiekarp.foundation.logger.Log
import net.rickiekarp.foundation.model.NotificationTokenData
import net.rickiekarp.loginserver.config.EmailServiceImpl
import net.rickiekarp.loginserver.dto.EmailDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.beans.factory.annotation.Autowired

@RestController
@RequestMapping("notify")
class NotificationApi {

    @Autowired
    var service: EmailServiceImpl? = null

    @Autowired
    var repo: ApplicationSettingsDao? = null

    @PostMapping(value = ["send"])
    fun sendMailContent(@RequestHeader(name = "X-Notification-Token") notificationToken: String, @RequestBody mail: EmailDto): ResponseEntity<ResultDTO> {
        val setting = repo!!.getApplicationSettingByIdentifier("notificationtoken")
        if (setting != null) {
            val notificationList: List<NotificationTokenData> = jacksonObjectMapper().readValue(setting.content!!)
            val notificationData = findNotificationData(notificationToken, notificationList)
            if (notificationData != null) {
                mail.subject = "(${notificationData.name}) ${mail.subject}" //Add notification job name to subject
                service!!.sendInfoMail(mail, notificationData.name!!)
                return ResponseEntity(ResultDTO("success"), HttpStatus.OK)
            } else {
                Log.DEBUG.info("Mail was not sent! Invalid notification token: $notificationToken")
            }
        }

        return ResponseEntity(ResultDTO("not_sent"), HttpStatus.OK)
    }

    private fun findNotificationData(notificationToken: String, notificationDataList: List<NotificationTokenData>): NotificationTokenData? {
        for (i in 0 until notificationDataList.size) {
            if (notificationDataList[i].token == notificationToken) {
                return notificationDataList[i]
            }
        }
        return null
    }
}
