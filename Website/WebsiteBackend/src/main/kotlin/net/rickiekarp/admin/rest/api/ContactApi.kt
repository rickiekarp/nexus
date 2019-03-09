package net.rickiekarp.admin.rest.api

import net.rickiekarp.admin.dao.InformationDAO
import net.rickiekarp.admin.dto.ContactDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api")
class ContactApi {

    @Autowired
    var repo: InformationDAO? = null

    @RequestMapping("contact")
    fun getContactInformation(): ContactDTO? {
        return repo!!.getContactInformation()
    }
}
