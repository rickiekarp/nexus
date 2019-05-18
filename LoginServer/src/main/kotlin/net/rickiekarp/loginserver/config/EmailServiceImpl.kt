package net.rickiekarp.loginserver.config

import net.rickiekarp.loginserver.dto.EmailDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class EmailServiceImpl {

    private val notifications: HashMap<Int, Pair<String, String>> = hashMapOf(
            1 to Pair("Database Backup", "Database was backed up!")
    )

    @Value("\${spring.application.email}")
    private val contactEmail: String? = null

    @Autowired
    var emailSender: JavaMailSender? = null

    fun sendMail(email: EmailDto) {
        val message = SimpleMailMessage()
        message.setTo(email.to)
        message.subject = email.subject
        message.text = email.message
        emailSender!!.send(message)
    }

    fun sendMailByType(type: Int) {
        val mapEntry = notifications[type]
        if (mapEntry != null) {
            val mail = EmailDto()
            mail.to = contactEmail!!
            mail.subject = mapEntry.first
            mail.message = mapEntry.second
            sendMail(mail)
        } else {
            println("Notification with type '$type' not found!")
        }
    }
}
