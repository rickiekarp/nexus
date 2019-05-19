package net.rickiekarp.foundation.data.dao

import net.rickiekarp.foundation.data.dto.ApplicationSettingDto

interface ApplicationSettingsDao {
    fun getApplicationSettingByIdentifier(settingIdentifier: String): ApplicationSettingDto?
}