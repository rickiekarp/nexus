package net.rickiekarp.homeserver.dao

import net.rickiekarp.homeserver.dto.ResumeDTO
import net.rickiekarp.homeserver.dto.SkillsDTO

interface ResumeDAO {
    fun getExperienceData(): List<ResumeDTO>
    fun getEducationData(): List<ResumeDTO>
    fun getSkillsData(): List<SkillsDTO>
}