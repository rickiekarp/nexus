package com.rkarp.foundation.dao

import com.rkarp.foundation.dao.entity.ApplicationEntity

interface UpdateDAO {
    fun findAll(): List<ApplicationEntity>
    fun findById(): List<ApplicationEntity>
    fun findByName(identifier: String, updateChannel: Int): List<ApplicationEntity>
    fun insert(employee: ApplicationEntity): Int
    fun update(employee: ApplicationEntity): Int
}
