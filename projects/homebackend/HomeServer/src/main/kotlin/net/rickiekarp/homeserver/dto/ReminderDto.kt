package net.rickiekarp.homeserver.dto

import java.util.Date

class ReminderDto {
    var id: Int = 0
    var user_id: Int = 0
    var dateAdded: Date? = null
    var description: String? = null
    var reminder_interval: Byte? = null
    var reminder_senddate: Date? = null
    var reminder_enddate: Date? = null
    var isDeleted: Boolean = false
    var lastUpdated: Date? = null

    override fun toString(): String {
        return "$description"
    }

}
