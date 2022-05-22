package net.rickiekarp.loginserver.dto

import java.io.Serializable
import java.util.*

class AppObjectDTO : Serializable {
    var serverVersion: String? = null
    var features: Properties? = null
        private set // the setter is private and has the default implementation

    fun setFeatureSettings(properties: Properties) {
        this.features = properties
    }
}