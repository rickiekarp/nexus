package net.rickiekarp.homeserver.dao

import net.rickiekarp.homeserver.dto.ApplicationDTO

interface UpdateDAO {
    fun findByName(identifier: String, updateChannel: Int): List<ApplicationDTO>
    fun insert(application: ApplicationDTO): Int
    fun update(application: ApplicationDTO): Int
}
