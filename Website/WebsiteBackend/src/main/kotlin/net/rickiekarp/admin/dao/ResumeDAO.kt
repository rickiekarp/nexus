package net.rickiekarp.admin.dao

import net.rickiekarp.admin.dto.SkillsDTO

interface ResumeDAO {
    fun getSkillsData(): List<SkillsDTO>
}