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

    private val SELECT_REMINDER_LIST = "select * from tracking_todo where users_id = ? AND (reminder_enddate IS NULL OR reminder_enddate < timestamp(DATE_SUB(NOW(), INTERVAL -? DAY)))"

    @Autowired
    private val dataSource: DataSource? = null

    override fun getActiveReminders(userId: Int, daysInFuture: Int): List<ReminderDto> {
        val notesList = ArrayList<ReminderDto>()

        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(SELECT_REMINDER_LIST)
            stmt!!.setInt(1, userId)
            stmt.setInt(2, daysInFuture)
            val rs = stmt.executeQuery()
            while (rs.next()) {
                val userVO = ReminderDto()
                userVO.user_id = rs.getInt("users_id")
                userVO.reminder_senddate = rs.getDate("reminder_senddate")
                userVO.description = rs.getString("description")
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

}
