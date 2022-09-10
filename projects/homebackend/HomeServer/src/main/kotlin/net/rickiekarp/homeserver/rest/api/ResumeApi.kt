package net.rickiekarp.homeserver.rest.api

import net.rickiekarp.homeserver.dao.ResumeDAO
import net.rickiekarp.homeserver.dto.ResumeDTO
import net.rickiekarp.homeserver.dto.SkillsDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class ResumeApi {

    @Autowired
    var repo: ResumeDAO? = null

    @GetMapping("resume/experience")
    fun experienceData(): List<ResumeDTO> {
        return repo!!.getExperienceData()
    }

    @GetMapping("resume/education")
    fun educationData(): List<ResumeDTO> {
        return repo!!.getEducationData()
    }

    @GetMapping("resume/skills")
    fun skillsData(): List<SkillsDTO> {
        return repo!!.getSkillsData()
    }
}
