package net.rickiekarp.homeserver.dto

import java.util.Date

class ShoppingNoteDto {
    var id: Int = 0
    var title: String? = null
    var price: Double = 0.toDouble()
    var user_id: Int = 0
    var dateBought: Date? = null
    var store_id: Byte? = null
    var dateAdded: Date? = null
    var lastUpdated: Date? = null

    override fun toString(): String {
        return "ShoppingNoteDto{" +
                "id=" + id +
                ", title='" + title + '\''.toString() +
                ", user_id=" + user_id +
                ", price=" + price +
                ", store_id=" + store_id
    }
}
