package com.projekt.backend.mapper

import com.projekt.backend.dto.exception.ErrorDTO
import javax.ws.rs.NotFoundException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class NotFoundExceptionMapper : ExceptionMapper<NotFoundException> {

    override fun toResponse(ex: NotFoundException): Response {
        return Response.status(404).entity(defaultJSON(ex)).type(MediaType.APPLICATION_JSON).build()
    }

    private fun defaultJSON(exception: Exception): ErrorDTO {
        return ErrorDTO(exception.message!!)
    }
}