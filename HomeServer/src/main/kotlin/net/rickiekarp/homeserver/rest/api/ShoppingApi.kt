package net.rickiekarp.homeserver.rest.api

import net.rickiekarp.foundation.dto.exception.ResultDTO
import net.rickiekarp.homeserver.dao.ShoppingNoteDAO
import net.rickiekarp.homeserver.dto.ShoppingNoteDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

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
        val noteList = repo!!.getNotesFromUserId(getUserId())
        return ResponseEntity(noteList, HttpStatus.OK)
    }

    @PostMapping(value = ["add"])
    fun insertNote(@RequestBody noteDto: ShoppingNoteDto): ResponseEntity<ShoppingNoteDto?> {
        println("insertNote")
        noteDto.user_id = getUserId()
        val note = repo!!.insertShoppingNote(noteDto)
        return ResponseEntity(note, HttpStatus.OK)
    }

    @PostMapping(value = ["update"])
    fun updateItem(@RequestBody noteDto: ShoppingNoteDto): ResponseEntity<ResultDTO?> {
        println("updateBought")
        noteDto.user_id = getUserId()
        val noteDeleted = repo!!.updateShoppingNote(noteDto)
        return ResponseEntity(noteDeleted, HttpStatus.OK)
    }

    @PostMapping(value = ["markAsBought"])
    fun markAsBought(@RequestBody noteDto: ShoppingNoteDto): ResponseEntity<ResultDTO?> {
        println("markAsBought")
        noteDto.user_id = getUserId()
        val noteDeleted = repo!!.markAsBought(noteDto)
        return ResponseEntity(noteDeleted, HttpStatus.OK)
    }

    @PostMapping(value = ["remove"])
    fun remove(@RequestBody noteDto: ShoppingNoteDto): ResponseEntity<ResultDTO?> {
        println("remove")
        val noteDeleted = repo!!.removeItem(noteDto.id)
        return ResponseEntity(noteDeleted, HttpStatus.OK)
    }

    private fun getUserId(): Int {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication !is AnonymousAuthenticationToken) {
            return authentication.name.toInt()
        }
        return -1
    }
}
