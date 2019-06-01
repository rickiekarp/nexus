package net.rickiekarp.homeserver.rest.api

import net.rickiekarp.foundation.config.BaseConfig
import net.rickiekarp.foundation.data.dto.ResultDTO
import net.rickiekarp.homeserver.dao.ShoppingNoteDAO
import net.rickiekarp.homeserver.domain.ShoppingNoteList
import net.rickiekarp.homeserver.domain.ShoppingStoreList
import net.rickiekarp.homeserver.dto.ShoppingNoteDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("shopping")
class ShoppingApi {

    @Autowired
    var repo: ShoppingNoteDAO? = null

    /**
     * Checks whether the user is allowed to execute the requested plugin
     * @param pluginIdentifierJson Plugin to check
     * @return True if user is allowed, false otherwise
     */
    @GetMapping(value = ["get"])
    fun getList(): ResponseEntity<List<ShoppingNoteDto>> {
        val noteList = repo!!.getNotesFromUserId(BaseConfig.get().getUserId())
        return ResponseEntity(noteList, HttpStatus.OK)
    }

    @PostMapping(value = ["add"])
    fun insertNote(@RequestBody noteDto: ShoppingNoteDto): ResponseEntity<ShoppingNoteDto?> {
        noteDto.user_id = BaseConfig.get().getUserId()
        val note = repo!!.insertShoppingNote(noteDto)
        return ResponseEntity(note, HttpStatus.OK)
    }

    @PostMapping(value = ["update"])
    fun updateItem(@RequestBody noteDto: ShoppingNoteDto): ResponseEntity<ResultDTO?> {
        noteDto.user_id = BaseConfig.get().getUserId()
        val noteDeleted = repo!!.updateShoppingNote(noteDto)
        return ResponseEntity(noteDeleted, HttpStatus.OK)
    }

    @PostMapping(value = ["markAsBought"])
    fun markAsBought(@RequestBody noteDto: ShoppingNoteDto): ResponseEntity<ResultDTO?> {
        noteDto.user_id = BaseConfig.get().getUserId()
        val noteDeleted = repo!!.markAsBought(noteDto)
        return ResponseEntity(noteDeleted, HttpStatus.OK)
    }

    @PostMapping(value = ["remove"])
    fun remove(@RequestBody noteDto: ShoppingNoteDto): ResponseEntity<ResultDTO?> {
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
