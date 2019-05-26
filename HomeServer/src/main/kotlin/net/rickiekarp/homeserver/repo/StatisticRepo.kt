package net.rickiekarp.homeserver.repo

import net.rickiekarp.foundation.utils.DatabaseUtil
import net.rickiekarp.homeserver.dao.StatisticDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.sql.SQLException
import javax.sql.DataSource

@Repository
open class StatisticRepo : StatisticDAO {
    private val SELECT_SHOPPING_STATISTIC = "select sum(sn.price) as value, ss.name from shopping_note sn join shopping_store ss on ss.id = sn.store_id where sn.users_id = ? AND sn.dateBought BETWEEN CURDATE() - INTERVAL ? DAY AND CURDATE() group by store_id"

    @Autowired
    private val dataSource: DataSource? = null

    override fun getShoppingStatistic(userId: Int, days: Int): HashMap<String, Double> {
        var stmt: PreparedStatement? = null
        val costMap = HashMap<String, Double>()
        try {
            stmt = dataSource!!.connection.prepareStatement(SELECT_SHOPPING_STATISTIC)
            stmt!!.setInt(1, userId)
            stmt.setInt(2, days)

            val resultSet = stmt.executeQuery()
            while (resultSet.next()) {
                costMap[resultSet.getString("name")] = resultSet.getDouble("value")
            }
        } catch (e: SQLException) {
            // Log.DEBUG.error("Exception", e);
            throw RuntimeException(e)
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return costMap
    }
}
