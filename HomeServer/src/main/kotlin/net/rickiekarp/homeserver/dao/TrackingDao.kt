package net.rickiekarp.homeserver.dao

import net.rickiekarp.homeserver.dto.WeightDto

interface TrackingDao {
    fun getWeightHistory(userId: Int, limit: Int): List<WeightDto>
}