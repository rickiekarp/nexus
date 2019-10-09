package net.rickiekarp.homeserver.rest.api

import net.rickiekarp.homeserver.dao.UpdateDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("app")
class ApplicationApi {

    @Autowired
    var repo: UpdateDAO? = null

    @GetMapping("update")
    fun getUpdateInfo(
//            @QueryParam("identifier") appIdentifier: String,
//            @QueryParam("channel") updateChannel: Int
    ): ResponseEntity<String> {
//        val application = repo!!.findByName(appIdentifier, updateChannel)
        return ResponseEntity("not_implemented_yet", HttpStatus.OK)
    }

    @GetMapping("changelog")
    fun getChangelog(
//            @QueryParam("identifier") appIdentifier: String
    ): ResponseEntity<String> {
//        val xmlString = FileParser.readFileAndReturnString(ServerContext.serverContext.getHomeDirPath(AppServer::class.java) + "/Resources/" + appIdentifier + "/changelog.xml")
        return ResponseEntity("not_implemented_yet", HttpStatus.OK)
    }
}
