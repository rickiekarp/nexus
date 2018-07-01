package com.rkarp.loginserver.dto

import com.rkarp.loginserver.factory.AppObjectBuilder
import java.io.Serializable
import java.util.*

class AppObjectDTO(builder: AppObjectBuilder) : Serializable {
    var serverVersion: String? = null
    var features: Properties? = null
        private set // the setter is private and has the default implementation

    fun setFeatureSettings(properties: Properties) {
        this.features = properties
    }
}