package com.rkarp.homeserver.rest.api

import com.rkarp.foundation.config.Configuration
import com.rkarp.foundation.parser.PropertiesParser
import org.json.JSONObject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.HashMap

@RestController
@RequestMapping("auth")
class AuthApi {

    /**
     * Checks whether the user is allowed to execute the requested plugin
     * @param pluginIdentifierJson Plugin to check
     * @return True if user is allowed, false otherwise
     */
    @RequestMapping(value = ["/validate"], method = arrayOf(RequestMethod.POST))
    fun validatePlugin(pluginIdentifierJson: String): ResponseEntity<String> {
        val requestJson = JSONObject(pluginIdentifierJson)
        val result = Configuration.properties.getProperty(requestJson.getString("plugin") + ".enable")
        return if (result == null) {
            throw RuntimeException("asdasd")
        } else {
            return ResponseEntity(result, HttpStatus.OK)
        }
    }

    @RequestMapping(
            value = ["/validateProperties"],
            method = arrayOf(RequestMethod.GET)
    )
    fun validateProperties(pluginIdentifier: String): ResponseEntity<HashMap<String, HashMap<Any, Any>>> {
        val filteredProperties = PropertiesParser.filterPropertiesAndReplaceIdentifier(Configuration.properties, pluginIdentifier)
        if (filteredProperties.size == 0) {
            //throw NotFoundException()
        }
        val list = PropertiesParser.getPropertyMapWithIdentifier(filteredProperties, pluginIdentifier)
        return ResponseEntity(list, HttpStatus.OK)
    }
}
