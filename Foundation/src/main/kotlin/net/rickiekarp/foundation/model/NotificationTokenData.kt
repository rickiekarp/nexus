package net.rickiekarp.foundation.model

open class NotificationTokenData {
    var name: String? = null
    var token: String? = null
    var template: String? = null

    override fun toString(): String {
        return "NotificationTokenData(name=$name, token=$token)"
    }
}