package net.rickiekarp.admin.rest.api

import net.rickiekarp.admin.dto.HeroDTO
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.stream.Collectors

@RestController
@RequestMapping("api")
class ResumeApi {

    @RequestMapping("resume")
    fun getHeroes(): List<HeroDTO> {
        val r = Random()
        val names = arrayOf("A", "B", "C")

        return Arrays.stream(names)
                .map { name -> HeroDTO(r.ints(0, 20).findFirst().orElse(0), name) }
                .collect(Collectors.toList<HeroDTO>())

    }
}
