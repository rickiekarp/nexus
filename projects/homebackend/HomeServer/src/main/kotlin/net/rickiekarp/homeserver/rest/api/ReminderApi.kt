package net.rickiekarp.homeserver.rest.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.rickiekarp.foundation.config.BaseConfig
import net.rickiekarp.foundation.data.dao.ApplicationSettingsDao
import net.rickiekarp.foundation.data.dto.ResultDTO
import net.rickiekarp.foundation.core.services.mail.EmailService
import net.rickiekarp.foundation.data.dto.EmailDto
import net.rickiekarp.foundation.logger.Log
import net.rickiekarp.foundation.model.NotificationTokenData
import net.rickiekarp.homeserver.dao.ReminderDao
import net.rickiekarp.homeserver.dto.ReminderDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import kotlin.collections.HashMap

@RestController
@RequestMapping("reminder")
class ReminderApi {

    @Autowired
    var emailService: EmailService? = null

    @Autowired
    var settingsRepo: ApplicationSettingsDao? = null

    @Autowired
    var reminderDao: ReminderDao? = null

    @PostMapping(value = ["send"])
    fun sendReminders(@RequestHeader(name = "X-Notification-Token") notificationToken: String,
                      @RequestBody mail: EmailDto): ResponseEntity<ResultDTO> {
        val applicationSetting = settingsRepo!!.getApplicationSettingByIdentifier("notificationtoken")
        if (applicationSetting != null) {
            val notificationTokenList: List<NotificationTokenData> = jacksonObjectMapper().readValue(applicationSetting.content!!)
            val notificationData = emailService!!.findNotificationData(notificationToken, notificationTokenList)
            if (notificationData != null) {
                val reminderList = reminderDao!!.getActiveReminders(BaseConfig.get().getUserId())
                if (reminderList.isNotEmpty()) {
                    val reminderMap = getFinalReminders(reminderList)
                    if (reminderMap.size > 0) {
                        mail.additionalData = reminderMap
                        emailService!!.sendInfoMail(mail, notificationData)
                        reminderDao!!.updateReminderSendDate(reminderMap)
                    } else {
                        Log.DEBUG.info("No reminder data found for user (${BaseConfig.get().getUserId()}) after filtering!")
                    }
                } else {
                    Log.DEBUG.info("No reminders found for user (${BaseConfig.get().getUserId()})!")
                }
                return ResponseEntity(ResultDTO("success"), HttpStatus.OK)
            } else {
                Log.DEBUG.warn("Mail was not sent! Invalid notification token: $notificationToken")
            }
        }

        return ResponseEntity(ResultDTO("not_sent"), HttpStatus.OK)
    }

    @PostMapping(value = ["add"])
    fun addReminder(): ResponseEntity<ResultDTO> {
        return ResponseEntity(ResultDTO("not_available_yet"), HttpStatus.OK)
    }

    private fun getFinalReminders(reminderList: List<ReminderDto>): HashMap<String, ReminderDto> {
        val reminderMap = HashMap<String, ReminderDto>()
        for (i in reminderList.indices) {
            val sendDate = reminderList[i].reminder_senddate
            if (sendDate != null)  {
                if (!isSendingPending(
                                Instant.ofEpochMilli(sendDate.time).atZone(ZoneId.systemDefault()).toLocalDate(),
                                reminderList[i].reminder_interval!!)
                ) {
                    continue
                }
            }
            reminderMap[(i+1).toString()] = reminderList[i]
        }
        return reminderMap
    }

    private fun isSendingPending(fromDate: LocalDate, interval: Byte): Boolean {
        if (fromDate.plusDays(interval.toLong()).isEqual(LocalDate.now())) {
            return true
        }
        return false
    }
}
