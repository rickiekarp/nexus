package net.rickiekarp.homeserver.rest.api

import net.rickiekarp.foundation.config.BaseConfig
import net.rickiekarp.foundation.data.dto.ResultDTO
import net.rickiekarp.homeserver.dao.ShoppingNoteDAO
import net.rickiekarp.homeserver.domain.ShoppingNote
import net.rickiekarp.homeserver.domain.ShoppingNoteList
import net.rickiekarp.homeserver.domain.ShoppingStoreList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("shopping")
class ShoppingApi {

    @Autowired
    var repo: ShoppingNoteDAO? = null

    @GetMapping(value = ["get"])
    fun getList(): ResponseEntity<List<ShoppingNote>> {
        val noteList = repo!!.getNotesFromUserId(BaseConfig.get().getUserId())
        return ResponseEntity(noteList, HttpStatus.OK)
    }

    @PostMapping(value = ["add"])
    fun insertNote(@RequestBody noteDto: ShoppingNote): ResponseEntity<ShoppingNote?> {
        val noteDtoToInsert = ShoppingNote
            .newBuilder()
            .setId(noteDto.id)
            .setTitle(noteDto.title)
            .setPrice(noteDto.price)
            .setDateAdded(noteDto.dateAdded)
            .setDateBought(noteDto.dateBought)
            .setStoreId(noteDto.storeId)
            .setLastUpdated(noteDto.lastUpdated)
            .setUserId(BaseConfig.get().getUserId())
            .build()
        val note = repo!!.insertShoppingNote(noteDtoToInsert)
        return ResponseEntity(note, HttpStatus.OK)
    }

    @PostMapping(value = ["update"])
    fun updateItem(@RequestBody noteDto: ShoppingNote): ResponseEntity<ResultDTO?> {
        val noteDtoToDelete = ShoppingNote
            .newBuilder()
            .setId(noteDto.id)
            .setTitle(noteDto.title)
            .setPrice(noteDto.price)
            .setDateAdded(noteDto.dateAdded)
            .setDateBought(noteDto.dateBought)
            .setStoreId(noteDto.storeId)
            .setLastUpdated(noteDto.lastUpdated)
            .setUserId(BaseConfig.get().getUserId())
            .build()
        val noteDeleted = repo!!.updateShoppingNote(noteDtoToDelete)
        return ResponseEntity(noteDeleted, HttpStatus.OK)
    }

    @PostMapping(value = ["markAsBought"])
    fun markAsBought(@RequestBody noteDto: ShoppingNote): ResponseEntity<ResultDTO?> {
        val noteDtoToUpdate = ShoppingNote
            .newBuilder()
            .setId(noteDto.id)
            .setTitle(noteDto.title)
            .setPrice(noteDto.price)
            .setDateAdded(noteDto.dateAdded)
            .setDateBought(noteDto.dateBought)
            .setStoreId(noteDto.storeId)
            .setLastUpdated(noteDto.lastUpdated)
            .setUserId(BaseConfig.get().getUserId())
            .build()
        val noteDeleted = repo!!.markAsBought(noteDtoToUpdate)
        return ResponseEntity(noteDeleted, HttpStatus.OK)
    }

    @PostMapping(value = ["remove"])
    fun remove(@RequestBody noteDto: ShoppingNote): ResponseEntity<ResultDTO?> {
        val noteDeleted = repo!!.removeItem(noteDto.id)
        return ResponseEntity(noteDeleted, HttpStatus.OK)
    }

    @GetMapping(value = ["history"], produces = ["application/x-protobuf"])
    fun getHistory(): ResponseEntity<ShoppingNoteList> {
        val noteList = repo!!.getBoughtHistory(BaseConfig.get().getUserId())
        return ResponseEntity(noteList, HttpStatus.OK)
    }

    @GetMapping(value = ["stores"], produces = ["application/x-protobuf"])
    fun getStores(): ResponseEntity<ShoppingStoreList> {
        val storeList = repo!!.getStores()
        return ResponseEntity(storeList, HttpStatus.OK)
    }
}
