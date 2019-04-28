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
    private val FIND_BY_USER_ID = "SELECT * FROM shopping_note WHERE users_id = ? AND dateBought IS NULL AND isDeleted = false"
    private val INSERT = "insert into shopping_note(title, description,  users_id, dateAdded, lastUpdated, isDeleted) values (?, null, ?, now(), null, false)"
    private val MARK_AS_BOUGHT = "UPDATE shopping_note SET dateBought = now(), lastUpdated = now() WHERE id = ?"
    private val UPDATE = "UPDATE shopping_note SET title = ?, lastUpdated = now() WHERE id = ?"
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
                notesList.add(extractUserFromResultSet(rs))
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
        val noteDto = ShoppingNoteDto()
        noteDto.id = resultSet.getInt("id")
        noteDto.title = resultSet.getString("title")
        noteDto.price = resultSet.getDouble("price")
        noteDto.dateAdded = resultSet.getDate("dateAdded")
        noteDto.dateBought = resultSet.getDate("dateBought")
        noteDto.store_id = resultSet.getByte("store_id")
        noteDto.lastUpdated = resultSet.getDate("lastUpdated")
        return noteDto
    }

    override fun insertShoppingNote(note: ShoppingNoteDto): ShoppingNoteDto? {
        var insertedNote: ShoppingNoteDto? = null
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)
            stmt!!.setString(1, note.title)
            stmt.setInt(2, note.user_id)

            stmt.execute()

            val resultSet = stmt.generatedKeys
            if (resultSet.next()) {
                insertedNote = ShoppingNoteDto()
                insertedNote.id = resultSet.getInt(1)
                insertedNote.title = note.title
                println("Inserted: $note")
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

    override fun updateShoppingNote(note: ShoppingNoteDto): ResultDTO {
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(UPDATE)
            stmt!!.setString(1, note.title)
            stmt.setInt(2, note.id)
            stmt.executeUpdate()
            println("Updated: $note")
            return ResultDTO("success")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return ResultDTO("failed")
    }

    override fun markAsBought(note: ShoppingNoteDto): ResultDTO {
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(MARK_AS_BOUGHT)
            stmt!!.setInt(1, note.id)
            stmt.executeUpdate()
            println("Bought: $note")
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
            println("Removed note: $noteid")
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
