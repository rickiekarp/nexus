package net.rickiekarp.loginserver.dao

interface UpdateDAO {
    fun findAll(): List<ApplicationEntity>
    fun findById(): List<ApplicationEntity>
    fun findByName(identifier: String, updateChannel: Int): List<ApplicationEntity>
    fun insert(application: ApplicationEntity): Int
    fun update(application: ApplicationEntity): Int
}
