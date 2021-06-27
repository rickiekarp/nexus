package com.projekt.appserver.rest.api

import com.projekt.appserver.dao.NoteDaoImpl
import com.projekt.appserver.dto.NoteDTO
import com.projekt.backend.dto.exception.ErrorDTO
import com.projekt.backend.dto.exception.ResultDTO
import com.projekt.backend.model.User
import com.projekt.backend.security.Secured
import java.io.IOException
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.SecurityContext

@Path("note")
class NoteApi {
    private val noteImpl: NoteDaoImpl = NoteDaoImpl()

    @GET
    @Path("getAll")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Throws(IOException::class)
    fun getAll(@Context securityContext: SecurityContext): Response {
        val user = securityContext.userPrincipal as User
        return Response.status(200).entity(noteImpl.getAllNotes(user.userId)).build()
    }

    @POST
    @Path("add")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Throws(IOException::class)
    fun addNote(@Context securityContext: SecurityContext, note: NoteDTO): Response {
        val user = securityContext.userPrincipal as User
        note.setUserId(user.userId)
        val noteInfo = noteImpl.add(note)
        return if (noteInfo.noteId > 0) {
            Response.status(200).entity(noteInfo).build()
        } else {
            Response.status(404).entity(ErrorDTO("Note could not be added!")).build()
        }
    }

    @POST
    @Path("update")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Throws(IOException::class)
    fun editNote(note: NoteDTO): Response {
        val isSuccess = noteImpl.update(note)
        return if (isSuccess) {
            Response.status(200).entity(note).build()
//            Response.status(200).entity(ResultDTO("Note has been updated!")).build()
        } else {
            Response.status(404).entity(ErrorDTO("Note could not be updated!")).build()
        }
    }

    @POST
    @Path("remove")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Throws(IOException::class)
    fun removeNote(note: NoteDTO): Response {
        val isSuccess = noteImpl.remove(note)
        return if (isSuccess) {
            Response.status(200).entity(ResultDTO("Note has been deleted!")).build()
        } else {
            Response.status(404).entity(ErrorDTO("Note could not be deleted!")).build()
        }
    }
}