package net.rickiekarp.homeserver.dao

import net.rickiekarp.foundation.dto.exception.ResultDTO
import net.rickiekarp.homeserver.dto.ShoppingNoteDto

interface ShoppingNoteDAO {
    fun getNotesFromUserId(id: Int): List<ShoppingNoteDto>
    fun insertShoppingNote(note: ShoppingNoteDto, id: Int): ShoppingNoteDto?
    fun updateShoppingNote(user: ShoppingNoteDto, id: Int): ResultDTO
    fun removeItem(itemid: Int): ResultDTO
}