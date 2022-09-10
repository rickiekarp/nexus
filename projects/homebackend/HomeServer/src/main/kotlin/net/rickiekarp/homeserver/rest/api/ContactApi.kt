package net.rickiekarp.homeserver.rest.api

import net.rickiekarp.homeserver.dao.InformationDAO
import net.rickiekarp.homeserver.dto.ContactDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class ContactApi {

    @Autowired
    var repo: InformationDAO? = null

    @GetMapping("contact")
    fun getContactInformation(): ContactDTO? {
        return repo!!.getContactInformation()
    }
}
