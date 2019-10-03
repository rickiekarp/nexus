package net.rickiekarp.homeserver.dto

import java.util.*

class ResumeDTO(
        var id: Int,
        var startDate: Date,
        var endDate: Date?,
        var name: String,
        var jobTitle: String,
        var description: ResumeDescriptionDTO
)
