package net.rickiekarp.loginserver.dto

class EmailDto {
    lateinit var to: String
    var subject: String? = null
    var message: String? = null

    override fun toString(): String {
        return "EmailDto(to=$to, subject=$subject, message=$message)"
    }
}
