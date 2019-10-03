package net.rickiekarp.homeserver.repo

import net.rickiekarp.foundation.utils.DatabaseUtil
import net.rickiekarp.homeserver.dao.InformationDAO
import net.rickiekarp.homeserver.dto.ContactDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

@Repository
open class InformationRepo : InformationDAO {

    private val FIND_FIRST_CONTACT = "SELECT * FROM contact WHERE contact_id = 1"

    @Autowired
    private val dataSource: DataSource? = null

    override fun getContactInformation(): ContactDTO? {
        var userVO: ContactDTO? = null
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(FIND_FIRST_CONTACT, Statement.RETURN_GENERATED_KEYS)

            val rs = stmt.executeQuery()
            if (rs.next()) {
                userVO = ContactDTO(
                        rs.getString("name"),
                        rs.getString("email")
                )
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return userVO
    }

}
