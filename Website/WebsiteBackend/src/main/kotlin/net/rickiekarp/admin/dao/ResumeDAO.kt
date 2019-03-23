package net.rickiekarp.admin.dao

import net.rickiekarp.admin.dto.ResumeDTO
import net.rickiekarp.admin.dto.SkillsDTO

interface ResumeDAO {
    fun getExperienceData(): List<ResumeDTO>
    fun getEducationData(): List<ResumeDTO>
    fun getSkillsData(): List<SkillsDTO>
}