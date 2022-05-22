package net.rickiekarp.homeserver.repo

import com.google.protobuf.Timestamp
import net.rickiekarp.foundation.data.dto.ResultDTO
import net.rickiekarp.foundation.logger.Log
import net.rickiekarp.foundation.utils.DatabaseUtil
import net.rickiekarp.homeserver.dao.ShoppingNoteDAO
import net.rickiekarp.homeserver.domain.ShoppingNote
import net.rickiekarp.homeserver.domain.ShoppingNoteList
import net.rickiekarp.homeserver.domain.ShoppingStore
import net.rickiekarp.homeserver.domain.ShoppingStoreList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

@Repository
open class ShoppingRepo : ShoppingNoteDAO {

    private val FIND_BY_USER_ID = "SELECT * FROM shopping_note WHERE users_id = ? AND dateBought IS NULL AND isDeleted = false"
    private val INSERT = "INSERT INTO shopping_note(title, description,  price, users_id, dateAdded, store_id, lastUpdated, isDeleted) VALUES (?, null, ?, ?, now(), ?, null, false)"
    private val UPDATE = "UPDATE shopping_note SET title = ?, price = ?, store_id = ?, lastUpdated = now() WHERE id = ?"
    private val REMOVE = "UPDATE shopping_note SET isDeleted = true, lastUpdated = now() WHERE id = ?"
    private val MARK_AS_BOUGHT = "UPDATE shopping_note SET dateBought = now(), lastUpdated = now() WHERE id = ?"
    private val FIND_HISTORY_BY_USER_ID = "SELECT * FROM shopping_note WHERE users_id = ? AND dateBought IS NOT NULL AND isDeleted = false ORDER BY dateBought desc"
    private val FIND_STORES = "SELECT * FROM shopping_store"

    @Autowired
    private val dataSource: DataSource? = null

    override fun getNotesFromUserId(id: Int): List<ShoppingNote> {
        var stmt: PreparedStatement? = null
        val notesList = ArrayList<ShoppingNote>()
        try {
            stmt = dataSource!!.connection.prepareStatement(FIND_BY_USER_ID)
            stmt!!.setInt(1, id)

            val rs = stmt.executeQuery()
            while (rs.next()) {
                notesList.add(extractNoteFromResultSet(rs))
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return notesList
    }

    override fun insertShoppingNote(note: ShoppingNote): ShoppingNote? {
        var insertedNote: ShoppingNote? = null
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)
            stmt!!.setString(1, note.title)
            stmt.setDouble(2, note.price)
            stmt.setInt(3, note.userId)
            stmt.setObject(4, note.storeId)

            stmt.execute()

            val resultSet = stmt.generatedKeys
            if (resultSet.next()) {
                insertedNote = ShoppingNote.newBuilder()
                    .setId(resultSet.getInt(1))
                    .setTitle(note.title)
                    .build()
                Log.DEBUG.debug("Inserted: $note")
            } else {
                Log.DEBUG.debug("There was no result when adding a new note!")
            }
        } catch (e: SQLException) {
            Log.DEBUG.error("Exception", e)
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return insertedNote
    }

    override fun updateShoppingNote(note: ShoppingNote): ResultDTO {
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(UPDATE)
            stmt!!.setString(1, note.title)
            stmt.setDouble(2, note.price)
            stmt.setObject(3, note.storeId)
            stmt.setInt(4, note.id)
            stmt.executeUpdate()
            Log.DEBUG.debug("Updated: $note")
            return ResultDTO("success")
        } catch (e: Exception) {
            Log.DEBUG.error("Exception", e)
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return ResultDTO("failed")
    }

    override fun markAsBought(note: ShoppingNote): ResultDTO {
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(MARK_AS_BOUGHT)
            stmt!!.setInt(1, note.id)
            stmt.executeUpdate()
            Log.DEBUG.debug("Bought: $note")
            return ResultDTO("success")
        } catch (e: Exception) {
            Log.DEBUG.error("Exception", e)
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return ResultDTO("failed")
    }

    override fun removeItem(itemid: Int): ResultDTO {
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(REMOVE)
            stmt!!.setInt(1, itemid)
            stmt.executeUpdate()
            Log.DEBUG.debug("Removed note: $itemid")
            return ResultDTO("success")
        } catch (e: Exception) {
            Log.DEBUG.error("Exception", e)
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return ResultDTO("failed")
    }

    override fun getBoughtHistory(user_id: Int): ShoppingNoteList {
        var stmt: PreparedStatement? = null
        val notesList = ShoppingNoteList.newBuilder()
        try {
            stmt = dataSource!!.connection.prepareStatement(FIND_HISTORY_BY_USER_ID)
            stmt!!.setInt(1, user_id)

            val rs = stmt.executeQuery()
            while (rs.next()) {
                notesList.addNote(extractNoteFromResultSet(rs))
            }
        } catch (e: SQLException) {
            // Log.DEBUG.error("Exception", e);
            throw RuntimeException(e)
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        val notes = notesList.build()
        Log.DEBUG.debug("NoteHistory size: ${notes.noteCount}")
        return notes
    }

    override fun getStores(): ShoppingStoreList {
        var stmt: PreparedStatement? = null
        val storeList = ShoppingStoreList.newBuilder()
        try {
            stmt = dataSource!!.connection.prepareStatement(FIND_STORES)

            val rs = stmt.executeQuery()
            while (rs.next()) {
                val store = ShoppingStore.newBuilder()
                        .setId(rs.getInt("id"))
                        .setName(rs.getString("name"))
                        .build()
                storeList.addStore(store)
            }
        } catch (e: SQLException) {
            // Log.DEBUG.error("Exception", e);
            throw RuntimeException(e)
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return storeList.build()
    }

    @Throws(SQLException::class)
    private fun extractNoteFromResultSet(resultSet: ResultSet): ShoppingNote {
        return ShoppingNote
                .newBuilder()
                .setId(resultSet.getInt("id"))
                .setTitle(resultSet.getString("title"))
                .setPrice(resultSet.getDouble("price"))
                .setDateAdded(Timestamp.newBuilder().setSeconds(resultSet.getTimestamp("dateAdded").time).build())
                .setDateBought(Timestamp.newBuilder().setSeconds(resultSet.getTimestamp("dateBought").time).build())
                .setStoreId(resultSet.getInt("store_id"))
                .setLastUpdated(Timestamp.newBuilder().setSeconds(resultSet.getTimestamp("lastUpdated").time).build())
                .build()
    }
}
