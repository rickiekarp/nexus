package net.rickiekarp.homeserver.rest.api

import net.rickiekarp.foundation.config.BaseConfig
import net.rickiekarp.homeserver.dao.TrackingDao
import net.rickiekarp.homeserver.dto.WeightDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("tracking")
class TrackingApi {

    @Autowired
    var trackingDao: TrackingDao? = null

    @GetMapping(value = ["weight"])
    fun getWeightList(@RequestHeader(name = "X-Limit") limitEntries: Int): ResponseEntity<List<WeightDto>> {
        val weightList = trackingDao!!.getWeightHistory(BaseConfig.get().getUserId(), limitEntries)
        return ResponseEntity(weightList, HttpStatus.OK)
    }
}