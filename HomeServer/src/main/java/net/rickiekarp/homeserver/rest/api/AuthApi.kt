package net.rickiekarp.homeserver.rest.api

import net.rickiekarp.foundation.parser.PropertiesParser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("auth")
class AuthApi {

    /**
     * Checks whether the user is allowed to execute the requested plugin
     * @param pluginIdentifierJson Plugin to check
     * @return True if user is allowed, false otherwise
     */
    @RequestMapping(value = ["/validate"], method = [RequestMethod.POST])
    fun validatePlugin(pluginIdentifier: String): ResponseEntity<String> {
        val result = net.rickiekarp.foundation.config.Configuration.properties.getProperty(pluginIdentifier + ".enable")
        return if (result == null) {
            throw RuntimeException("asdasd")
        } else {
            return ResponseEntity(result, HttpStatus.OK)
        }
    }

    @RequestMapping(
            value = ["/validateProperties"],
            method = [RequestMethod.GET]
    )
    fun validateProperties(pluginIdentifier: String): ResponseEntity<HashMap<String, HashMap<Any, Any>>> {
        val filteredProperties = PropertiesParser.filterPropertiesAndReplaceIdentifier(net.rickiekarp.foundation.config.Configuration.properties, pluginIdentifier)
        if (filteredProperties.size == 0) {
            //throw NotFoundException()
        }
        val list = PropertiesParser.getPropertyMapWithIdentifier(filteredProperties, pluginIdentifier)
        return ResponseEntity(list, HttpStatus.OK)
    }
}
