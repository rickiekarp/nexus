package net.rickiekarp.loginserver.dao

import net.rickiekarp.loginserver.dto.WorldDTO

interface WorldsDAO {
    fun getWorldList(): List<WorldDTO>
}
