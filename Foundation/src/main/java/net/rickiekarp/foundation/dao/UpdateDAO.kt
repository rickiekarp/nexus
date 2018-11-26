package net.rickiekarp.foundation.dao

import net.rickiekarp.foundation.dao.entity.ApplicationEntity

interface UpdateDAO {
    fun findAll(): List<net.rickiekarp.foundation.dao.entity.ApplicationEntity>
    fun findById(): List<net.rickiekarp.foundation.dao.entity.ApplicationEntity>
    fun findByName(identifier: String, updateChannel: Int): List<net.rickiekarp.foundation.dao.entity.ApplicationEntity>
    fun insert(employee: net.rickiekarp.foundation.dao.entity.ApplicationEntity): Int
    fun update(employee: net.rickiekarp.foundation.dao.entity.ApplicationEntity): Int
}
