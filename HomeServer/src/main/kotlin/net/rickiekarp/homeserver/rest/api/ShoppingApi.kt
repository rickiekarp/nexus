package net.rickiekarp.homeserver.rest.api

import net.rickiekarp.foundation.dto.exception.ResultDTO
import net.rickiekarp.homeserver.dao.ShoppingNoteDAO
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
        println("getList")
        val noteList = repo!!.getNotesFromUserId(1)
        return ResponseEntity(noteList, HttpStatus.OK)
    }

    @PostMapping(value = ["add"])
    fun insertNote(@RequestBody noteDto: ShoppingNoteDto): ResponseEntity<ShoppingNoteDto?> {
        println("insertNote")
        val noteList = repo!!.insertShoppingNote(noteDto, 1)
        return ResponseEntity(noteList, HttpStatus.OK)
    }

    @PostMapping(value = ["update"])
    fun updateBought(@RequestBody noteDto: ShoppingNoteDto): ResponseEntity<ResultDTO?> {
        println("updateBought")
        val noteDeleted = repo!!.updateShoppingNote(noteDto, 1)
        return ResponseEntity(noteDeleted, HttpStatus.OK)
    }

    fun remove(@RequestBody noteDto: ShoppingNoteDto): ResponseEntity<ResultDTO?> {
        println("remove")
        val noteDeleted = repo!!.removeItem(noteDto.id)
        return ResponseEntity(noteDeleted, HttpStatus.OK)
    }
}
