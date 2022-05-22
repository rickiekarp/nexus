package net.rickiekarp.foundation.core.services.mail

import freemarker.template.Configuration
import net.rickiekarp.foundation.data.dto.EmailDto
import net.rickiekarp.foundation.model.NotificationTokenData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils

@Component
class EmailService {

    @Autowired
    var sender: JavaMailSender? = null

    @Value("\${spring.mail.username}")
    private val smtpEmail: String? = null

    @Autowired
    private val freemarkerConfig: Configuration? = null

    @Throws(Exception::class)
    fun sendInfoMail(mail: EmailDto, notificationTokenData: NotificationTokenData) {
        val message = sender!!.createMimeMessage()
        val helper = MimeMessageHelper(message)

        val model = extractModel(mail, notificationTokenData)

        // set loading location to src/main/resources
        freemarkerConfig!!.setClassForTemplateLoading(this.javaClass, "/templates/mail/")

        val template = freemarkerConfig.getTemplate("${notificationTokenData.template}.ftl")
        val templateContentText = FreeMarkerTemplateUtils.processTemplateIntoString(template, model)

        helper.setFrom(smtpEmail!!)
        helper.setTo(mail.to)
        helper.setSubject(mail.subject)
        helper.setText(templateContentText, true) // set to html

        sender!!.send(message)
    }

    @Throws(Exception::class)
    fun sendFromByteArray(mail: EmailDto, bytes: ByteArray) {
        val message = sender!!.createMimeMessage()
        val helper = MimeMessageHelper(message)

        helper.setFrom(smtpEmail!!)
        helper.setTo(mail.to)
        helper.setSubject(mail.subject)
        helper.setText(String(bytes), false) // set to html

        sender!!.send(message)
    }

    private fun extractModel(mail: EmailDto, notificationTokenData: NotificationTokenData): Map<String, Any> {
        val model = HashMap<String, Any>()
        model["message"] = mail.message
        model["additionalData"] = mail.additionalData as Map<*, *>
        model["job"] = notificationTokenData.name as String
        return model
    }

    fun findNotificationData(notificationToken: String, notificationDataList: List<NotificationTokenData>): NotificationTokenData? {
        for (i in 0 until notificationDataList.size) {
            if (notificationDataList[i].token == notificationToken) {
                return notificationDataList[i]
            }
        }
        return null
    }
}
