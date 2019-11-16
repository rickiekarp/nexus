package net.rickiekarp.homeserver.repo

import net.rickiekarp.foundation.utils.DatabaseUtil
import net.rickiekarp.homeserver.dao.ReminderDao
import net.rickiekarp.homeserver.dto.ReminderDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.sql.SQLException
import javax.sql.DataSource

@Repository
open class ReminderRepo : ReminderDao {

    private val SELECT_REMINDER_LIST = "select * from tracking_todo where users_id = ? AND reminder_startdate < now() AND (reminder_enddate IS NULL OR reminder_enddate > now())"
    private val UPDATE_REMINDER_SEND_DATE = "update tracking_todo set reminder_senddate = now(), lastUpdated = now() where id in (?)"

    @Autowired
    private val dataSource: DataSource? = null

    override fun getActiveReminders(userId: Int): List<ReminderDto> {
        val notesList = ArrayList<ReminderDto>()

        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(SELECT_REMINDER_LIST)
            stmt!!.setInt(1, userId)
            val rs = stmt.executeQuery()
            while (rs.next()) {
                val userVO = ReminderDto()
                userVO.id = rs.getInt("id")
                userVO.user_id = rs.getInt("users_id")
                userVO.dateAdded = rs.getDate("dateAdded")
                userVO.description = rs.getString("description")
                userVO.reminder_interval = rs.getByte("reminder_interval")
                userVO.reminder_startdate = rs.getDate("reminder_startdate")
                userVO.reminder_senddate = rs.getDate("reminder_senddate")
                userVO.reminder_enddate = rs.getDate("reminder_enddate")
                userVO.isDeleted = rs.getBoolean("isDeleted")
                userVO.lastUpdated = rs.getDate("lastUpdated")
                notesList.add(userVO)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return notesList
    }

    override fun updateReminderSendDate(reminderListToUpdate: Map<String, ReminderDto>) {
        val reminderIdList = ArrayList<Int>()
        reminderListToUpdate.forEach { (_, v) ->
            reminderIdList.add(v.id)
        }
        val output = reminderIdList.joinToString(",", prefix = "", postfix = "", limit = reminderIdList.size, truncated = "")
        val query = UPDATE_REMINDER_SEND_DATE.replace("?", output)

        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(query)
            stmt.executeUpdate()
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
    }

}
