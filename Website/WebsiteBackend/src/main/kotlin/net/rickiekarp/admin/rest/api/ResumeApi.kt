package net.rickiekarp.admin.rest.api

import net.rickiekarp.admin.dao.ResumeDAO
import net.rickiekarp.admin.dto.HeroDTO
import net.rickiekarp.admin.dto.SkillsDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.stream.Collectors

@RestController
@RequestMapping("api")
class ResumeApi {

    @Autowired
    var repo: ResumeDAO? = null

    @RequestMapping("resume/experience")
    fun experienceData(): List<HeroDTO> {
        val r = Random()
        val names = arrayOf("A", "B", "C")

        return Arrays.stream(names)
                .map { name -> HeroDTO(r.ints(0, 20).findFirst().orElse(0), name) }
                .collect(Collectors.toList<HeroDTO>())

    }

    @RequestMapping("resume/education")
    fun educationData(): List<HeroDTO> {
        val r = Random()
        val names = arrayOf("A", "B", "C")

        return Arrays.stream(names)
                .map { name -> HeroDTO(r.ints(0, 20).findFirst().orElse(0), name) }
                .collect(Collectors.toList<HeroDTO>())

    }

    @RequestMapping("resume/skills")
    fun skillsData(): List<SkillsDTO> {
        return repo!!.getSkillsData()
    }
}
