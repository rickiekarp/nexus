package com.projekt.backend.mapper

import com.projekt.backend.dto.exception.ErrorDTO
import javax.ws.rs.NotAuthorizedException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class NotAuthorizedMapper : ExceptionMapper<NotAuthorizedException> {

    override fun toResponse(ex: NotAuthorizedException): Response {
        return Response.status(401).entity(defaultJSON(ex)).type(MediaType.APPLICATION_JSON).build()
    }

    private fun defaultJSON(exception: Exception): ErrorDTO {
        return ErrorDTO(exception.message!!)
    }
}