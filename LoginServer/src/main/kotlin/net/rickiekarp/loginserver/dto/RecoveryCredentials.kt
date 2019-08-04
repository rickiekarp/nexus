package net.rickiekarp.loginserver.dto

open class RecoveryCredentials {
    var username: String? = null
    var lostPasswordHash: String? = null
    var newPassword: String? = null
}