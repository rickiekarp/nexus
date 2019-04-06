package net.rickiekarp.homeserver.repo

import net.rickiekarp.foundation.dto.exception.ResultDTO
import net.rickiekarp.foundation.utils.DatabaseUtil
import net.rickiekarp.homeserver.dao.ShoppingNoteDAO
import net.rickiekarp.homeserver.dto.ShoppingNoteDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

@Repository
open class ShoppingNoteRepo : ShoppingNoteDAO {
    private val FIND_BY_USER_ID = "SELECT * FROM shopping_note WHERE users_id = ?"
    private val INSERT = "insert into shopping_note(title, description,  users_id, dateAdded, lastUpdated, isDeleted) values (?, null, ?, now(), null, false)"
    private val UPDATE_BOUGHT = "UPDATE shopping_note SET lastUpdated = now() WHERE id = ?"
    private val REMOVE = "UPDATE shopping_note SET isDeleted = true, lastUpdated = now() WHERE id = ?"

    @Autowired
    private val dataSource: DataSource? = null

    override fun getNotesFromUserId(id: Int): List<ShoppingNoteDto> {
        var stmt: PreparedStatement? = null
        val notesList = ArrayList<ShoppingNoteDto>()
        try {
            stmt = dataSource!!.connection.prepareStatement(FIND_BY_USER_ID)
            stmt!!.setInt(1, id)

            val rs = stmt.executeQuery()

            while (rs.next()) {
                val user = ShoppingNoteDto()
                user.id = rs.getInt("id")
                user.title = rs.getString("title")
                user.dateAdded = rs.getDate("dateAdded")
                user.lastUpdated = rs.getDate("lastUpdated")

                notesList.add(user)
            }
        } catch (e: SQLException) {
            // e.printStackTrace();
            throw RuntimeException(e)
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return notesList
    }

    @Throws(SQLException::class)
    private fun extractUserFromResultSet(resultSet: ResultSet): ShoppingNoteDto {
        val userVO = ShoppingNoteDto()
        userVO.id = resultSet.getInt("id")
        userVO.title = resultSet.getString("title")
        userVO.dateAdded = resultSet.getDate("dateAdded")
        userVO.lastUpdated = resultSet.getDate("lastUpdated")
        return userVO
    }

    override fun insertShoppingNote(note: ShoppingNoteDto, id: Int): ShoppingNoteDto? {
        var insertedNote: ShoppingNoteDto? = null
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)
            stmt!!.setString(1, note.title)
            stmt.setInt(2, note.user_id)

            println(note)

            stmt.execute()

            val resultSet = stmt.generatedKeys
            if (resultSet.next()) {
                insertedNote = ShoppingNoteDto()
                insertedNote.id = resultSet.getInt(1)
            } else {
                println("There was no result when adding a new note!")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return insertedNote
    }

    override fun updateShoppingNote(user: ShoppingNoteDto, id: Int): ResultDTO {
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(UPDATE_BOUGHT)
            stmt!!.setInt(1, id)
            stmt.executeUpdate()
            return ResultDTO("success")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return ResultDTO("failed")
    }

    override fun removeItem(noteid: Int): ResultDTO {
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(REMOVE)
            stmt!!.setInt(1, noteid)
            stmt.executeUpdate()
            return ResultDTO("success")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return ResultDTO("failed")
    }
}
