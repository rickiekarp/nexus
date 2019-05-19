package net.rickiekarp.foundation.data.dto

import java.util.*

class ApplicationSettingDto {

    var id: Int = 0
    var identifier: String? = null
    var content: String? = null
    var lastUpdated: Date? = null

    override fun toString(): String {
        return "Application{" +
                "identifier='" + identifier + '\''.toString() +
                ", version=" + content +
                ", updateEnable=" + lastUpdated +
                '}'.toString()
    }
}