package net.rickiekarp.homeserver.dao

import net.rickiekarp.homeserver.dto.ReminderDto

interface ReminderDao {
    fun getActiveReminders(userId: Int, daysInFuture: Int): List<ReminderDto>
    fun updateReminderSendDate(reminderListToUpdate: Map<String, ReminderDto>)
}