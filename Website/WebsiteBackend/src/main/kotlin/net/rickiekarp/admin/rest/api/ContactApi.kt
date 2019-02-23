package net.rickiekarp.admin.rest.api

import net.rickiekarp.admin.dto.ContactDTO
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api")
class ContactApi {

    @RequestMapping("contact")
    fun getHeroes(): ContactDTO {
        return ContactDTO("Rickie Karp", "contact@rickiekarp.net")

    }
}
