package net.rickiekarp.loginserver.dto

class EmailDto {
    lateinit var to: String
    lateinit var subject: String
    lateinit var message: String

    override fun toString(): String {
        return "EmailDto(to=$to, subject=$subject, message=$message)"
    }
}
