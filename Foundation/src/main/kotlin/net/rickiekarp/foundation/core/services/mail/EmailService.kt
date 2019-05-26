package net.rickiekarp.foundation.core.services.mail

import freemarker.template.Configuration
import net.rickiekarp.foundation.model.NotificationTokenData
import net.rickiekarp.foundation.data.dto.EmailDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils
import java.util.HashMap

@Component
class EmailService {

    @Autowired
    var sender: JavaMailSender? = null

    @Value("\${spring.mail.username}")
    private val smtpEmail: String? = null

    @Autowired
    private val freemarkerConfig: Configuration? = null

    fun sendMail(email: EmailDto) {
        val message = SimpleMailMessage()
        message.setTo(email.to)
        message.subject = email.subject
        message.text = email.message
        sender!!.send(message)
    }

    @Throws(Exception::class)
    fun sendInfoMail(email: EmailDto, jobIdentifier: String) {
        val message = sender!!.createMimeMessage()
        val helper = MimeMessageHelper(message)

        val additionalData = email.additionalData as LinkedHashMap<*, *>

        val model = HashMap<String, String>()
        model["message"] = email.message
        model["job"] = jobIdentifier
        additionalData.forEach { (k, v) ->
            model["$k"] = v as String
        }

        // set loading location to src/main/resources
        freemarkerConfig!!.setClassForTemplateLoading(this.javaClass, "/templates/")

        val template = freemarkerConfig.getTemplate("mail/cronjob.ftl")
        val templateContentText = FreeMarkerTemplateUtils.processTemplateIntoString(template, model)

        helper.setFrom(smtpEmail!!)
        helper.setTo(email.to)
        helper.setSubject(email.subject)
        helper.setText(templateContentText, true) // set to html

        sender!!.send(message)
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
