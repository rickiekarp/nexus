package net.rickiekarp.homeserver.dao

import net.rickiekarp.foundation.data.dto.ResultDTO
import net.rickiekarp.homeserver.domain.ShoppingNoteList
import net.rickiekarp.homeserver.dto.ShoppingNoteDto

interface ShoppingNoteDAO {
    fun getNotesFromUserId(id: Int): List<ShoppingNoteDto>
    fun insertShoppingNote(note: ShoppingNoteDto): ShoppingNoteDto?
    fun updateShoppingNote(note: ShoppingNoteDto): ResultDTO
    fun markAsBought(note: ShoppingNoteDto): ResultDTO
    fun removeItem(itemid: Int): ResultDTO
    fun getBoughtHistory(user_id: Int): ShoppingNoteList
}