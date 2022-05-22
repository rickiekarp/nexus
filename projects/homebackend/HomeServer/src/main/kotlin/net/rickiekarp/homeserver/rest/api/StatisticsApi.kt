package net.rickiekarp.homeserver.rest.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.rickiekarp.foundation.core.services.mail.EmailService
import net.rickiekarp.foundation.data.dao.ApplicationSettingsDao
import net.rickiekarp.foundation.data.dto.EmailDto
import net.rickiekarp.foundation.data.dto.ResultDTO
import net.rickiekarp.foundation.logger.Log
import net.rickiekarp.foundation.model.NotificationTokenData
import net.rickiekarp.homeserver.dao.StatisticDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("statistics")
class StatisticsApi {

    @Autowired
    var emailService: EmailService? = null

    @Autowired
    var settingsRepo: ApplicationSettingsDao? = null

    @Autowired
    var statisticRepo: StatisticDAO? = null

    @PostMapping(value = ["shoppingValue"])
    fun generateShoppingStatistic(@RequestHeader(name = "X-Notification-Token") notificationToken: String,
                                  @RequestHeader(name = "X-UserId") userId: Int,
                                  @RequestHeader(name = "X-Days") daysToLookBack: Int,
                                  @RequestBody mail: EmailDto): ResponseEntity<ResultDTO> {
        val applicationSetting = settingsRepo!!.getApplicationSettingByIdentifier("notificationtoken")
        if (applicationSetting != null) {
            val notificationTokenList: List<NotificationTokenData> = jacksonObjectMapper().readValue(applicationSetting.content!!)
            val notificationData = emailService!!.findNotificationData(notificationToken, notificationTokenList)
            if (notificationData != null) {
                val statisticData = statisticRepo!!.getShoppingStatistic(userId, daysToLookBack)
                if (statisticData.size > 0) {
                    mail.additionalData = statisticData
                    emailService!!.sendInfoMail(mail, notificationData)
                } else {
                    Log.DEBUG.info("No statistic data found!")
                }
                return ResponseEntity(ResultDTO("success"), HttpStatus.OK)
            } else {
                Log.DEBUG.info("Mail was not sent! Invalid notification token: $notificationToken")
            }
        }

        return ResponseEntity(ResultDTO("not_sent"), HttpStatus.OK)
    }
}
