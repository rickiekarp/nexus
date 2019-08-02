package net.rickiekarp.homeserver.dao

import net.rickiekarp.homeserver.dto.ReminderDto

interface ReminderDao {
    fun getActiveReminders(userId: Int, days: Int): List<ReminderDto>
}