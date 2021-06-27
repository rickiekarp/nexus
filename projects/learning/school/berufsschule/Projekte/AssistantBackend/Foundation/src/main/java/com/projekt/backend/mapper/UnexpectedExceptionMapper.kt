package com.projekt.backend.mapper

import com.projekt.backend.dto.exception.ErrorDTO
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class UnexpectedExceptionMapper : ExceptionMapper<Exception> {

    override fun toResponse(exception: Exception): Response {
        val builder = Response.status(Response.Status.BAD_REQUEST)
                .entity(defaultJSON(exception))
                .type(MediaType.APPLICATION_JSON)
        return builder.build()
    }

    private fun defaultJSON(exception: Exception): ErrorDTO {
        return ErrorDTO(exception.message!!)
    }
}