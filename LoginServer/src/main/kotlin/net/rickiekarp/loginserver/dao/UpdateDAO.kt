package net.rickiekarp.loginserver.dao

import net.rickiekarp.loginserver.dto.ApplicationDTO

interface UpdateDAO {
    fun findByName(identifier: String, updateChannel: Int): List<ApplicationDTO>
    fun insert(application: ApplicationDTO): Int
    fun update(application: ApplicationDTO): Int
}
