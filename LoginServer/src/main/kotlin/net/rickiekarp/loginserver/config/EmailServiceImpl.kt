package net.rickiekarp.loginserver.config

import net.rickiekarp.loginserver.dto.EmailDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class EmailServiceImpl {

    @Autowired
    var emailSender: JavaMailSender? = null

    fun sendMail(email: EmailDto) {
        val message = SimpleMailMessage()
        message.setTo(email.to)
        message.subject = email.subject
        message.text = email.message
        emailSender!!.send(message)
    }

}
