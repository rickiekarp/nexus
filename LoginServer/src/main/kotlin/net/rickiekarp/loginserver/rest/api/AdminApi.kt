package net.rickiekarp.loginserver.rest.api

import net.rickiekarp.foundation.config.Configuration
import net.rickiekarp.foundation.dto.exception.ResultDTO
import net.rickiekarp.loginserver.dto.KeyValuePairDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("admin")
class AdminApi {

    @RequestMapping(
            value = ["getFeatureFlags"],
            method = [RequestMethod.GET]
    )
    fun getFeatureFlags(): ResponseEntity<ArrayList<KeyValuePairDTO>> {
        val keyValueList = ArrayList<KeyValuePairDTO>()

        for (key in Configuration.properties.stringPropertyNames()) {
            val value = Configuration.properties.getProperty(key)
            val keyValuePair = KeyValuePairDTO(key, value)
            keyValueList.add(keyValuePair)
            println("$key: $value")
        }

        return ResponseEntity(keyValueList, HttpStatus.OK)
    }

    @RequestMapping(
            value = ["updateFeatureFlag"],
            method = arrayOf(RequestMethod.POST)
    )
    fun updateFeatureFlag(keyValue: KeyValuePairDTO): ResponseEntity<ResultDTO> {
        if (Configuration.properties.containsKey(keyValue.key)) {
            Configuration.properties.setProperty(keyValue.key, keyValue.value)
        } else {
            return ResponseEntity(ResultDTO("Key not found!"), HttpStatus.NOT_FOUND)
        }
        return ResponseEntity(ResultDTO("success"), HttpStatus.OK)
    }
}
