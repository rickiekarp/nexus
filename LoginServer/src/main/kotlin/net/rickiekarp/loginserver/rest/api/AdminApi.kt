package net.rickiekarp.loginserver.rest.api

import net.rickiekarp.foundation.config.BaseConfig
import net.rickiekarp.foundation.dto.exception.ResultDTO
import net.rickiekarp.loginserver.dto.KeyValuePairDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
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

        for (key in BaseConfig.get().application().stringPropertyNames()) {
            val value = BaseConfig.get().application().getProperty(key)
            val keyValuePair = KeyValuePairDTO(key, value)
            keyValueList.add(keyValuePair)
            println("$key: $value")
        }

        return ResponseEntity(keyValueList, HttpStatus.OK)
    }

    @RequestMapping(
            value = ["updateFeatureFlag"],
            method = [RequestMethod.POST]
    )
    fun updateFeatureFlag(@RequestBody keyValue: KeyValuePairDTO): ResponseEntity<ResultDTO> {
        if (BaseConfig.get().application().containsKey(keyValue.key)) {
            BaseConfig.get().application().setProperty(keyValue.key, keyValue.value)
        } else {
            return ResponseEntity(ResultDTO("key_not_found"), HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity(ResultDTO("success"), HttpStatus.OK)
    }
}
