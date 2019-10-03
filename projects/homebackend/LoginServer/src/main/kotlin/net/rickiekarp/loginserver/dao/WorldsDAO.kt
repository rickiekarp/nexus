package net.rickiekarp.loginserver.dao

import net.rickiekarp.loginserver.domain.WorldList

interface WorldsDAO {
    fun getWorldList(): WorldList
}
