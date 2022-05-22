package net.rickiekarp.homeserver.dao

import net.rickiekarp.foundation.data.dto.ResultDTO
import net.rickiekarp.homeserver.domain.ShoppingNote
import net.rickiekarp.homeserver.domain.ShoppingNoteList
import net.rickiekarp.homeserver.domain.ShoppingStoreList

interface ShoppingNoteDAO {
    fun getNotesFromUserId(id: Int): List<ShoppingNote>
    fun insertShoppingNote(note: ShoppingNote): ShoppingNote?
    fun updateShoppingNote(note: ShoppingNote): ResultDTO
    fun markAsBought(note: ShoppingNote): ResultDTO
    fun removeItem(itemid: Int): ResultDTO
    fun getBoughtHistory(user_id: Int): ShoppingNoteList
    fun getStores(): ShoppingStoreList
}