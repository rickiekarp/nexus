package com.rkarp.loginserver.rest.api

import com.rkarp.foundation.config.Configuration
import com.rkarp.foundation.config.ServerContext
import com.rkarp.loginserver.controller.TokenController
import com.rkarp.loginserver.dto.AppObjectDTO
import com.rkarp.loginserver.factory.AppObjectBuilder
import com.rkarp.foundation.model.Credentials
import com.rkarp.loginserver.dto.TokenDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("account")
class AccountApi {

    @RequestMapping(
            value = "login",
            method = arrayOf(RequestMethod.POST)
    )
    fun login(): ResponseEntity<AppObjectDTO> {
        return try {
            val dto = AppObjectDTO(AppObjectBuilder())
            dto.serverVersion = ServerContext.serverVersion
            dto.setFeatureSettings(Configuration.properties)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(null, HttpStatus.UNAUTHORIZED)
        }
    }

    @RequestMapping(
            value = "create",
            method = arrayOf(RequestMethod.POST)
    )
    fun create(credentials: Credentials): ResponseEntity<TokenDTO> {
        val user = ServerContext.loginDao.registerUser(credentials)
        val token: TokenDTO
        if (user != null) {

            // Issue a token for the user
            token = TokenController.issueToken(user)

            return ResponseEntity(token, HttpStatus.OK)
        } else {
            token = TokenDTO("null")
        }

        return ResponseEntity(token, HttpStatus.OK)
        //return ResponseEntity(ErrorDTO("User already registered!"), HttpStatus.NOT_FOUND)
    }
}
