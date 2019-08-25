package net.rickiekarp.homeserver.rest.api

import net.rickiekarp.homeserver.dao.TrackingDao
import net.rickiekarp.homeserver.dto.WeightDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.beans.factory.annotation.Autowired

@RestController
@RequestMapping("tracking")
class TrackingApi {

    @Autowired
    var trackingDao: TrackingDao? = null

    @GetMapping(value = ["weight"])
    fun getWeightList(@RequestHeader(name = "X-Limit") limitEntries: Int): ResponseEntity<List<WeightDto>> {
        val weightList = trackingDao!!.getWeightHistory(4, limitEntries)
        return ResponseEntity(weightList, HttpStatus.OK)
    }
}