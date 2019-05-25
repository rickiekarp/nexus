package net.rickiekarp.loginserver.rest.api

import net.rickiekarp.foundation.logger.Log
import net.rickiekarp.loginserver.dao.WorldsDAO
import net.rickiekarp.loginserver.domain.WorldList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/worlds")
class WorldsApi {

    @Autowired
    var repo: WorldsDAO? = null

    /**
     * Checks whether the user is allowed to execute the requested plugin
     * @param pluginIdentifierJson Plugin to check
     * @return True if user is allowed, false otherwise
     */
    @GetMapping(value = ["/get"], produces = ["application/x-protobuf"])
    fun getWorlds(): ResponseEntity<WorldList> {

        return try {
            // Authenticate the user using the credentials provided
            val worldList = repo!!.getWorldList()

            // Return the token on the response
            ResponseEntity(worldList, HttpStatus.OK)

        } catch (e: RuntimeException) {
            Log.DEBUG.error("Exception", e)
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
