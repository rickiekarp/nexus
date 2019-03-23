package net.rickiekarp.admin.rest.api

import net.rickiekarp.admin.dao.ResumeDAO
import net.rickiekarp.admin.dto.ResumeDTO
import net.rickiekarp.admin.dto.SkillsDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api")
class ResumeApi {

    @Autowired
    var repo: ResumeDAO? = null

    @RequestMapping("resume/experience")
    fun experienceData(): List<ResumeDTO> {
        return repo!!.getExperienceData()
    }

    @RequestMapping("resume/education")
    fun educationData(): List<ResumeDTO> {
        return repo!!.getEducationData()
    }

    @RequestMapping("resume/skills")
    fun skillsData(): List<SkillsDTO> {
        return repo!!.getSkillsData()
    }
}
