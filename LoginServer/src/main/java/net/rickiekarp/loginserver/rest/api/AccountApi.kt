package net.rickiekarp.loginserver.rest.api

import net.rickiekarp.foundation.model.Credentials
import net.rickiekarp.loginserver.controller.TokenController
import net.rickiekarp.loginserver.dto.AppObjectDTO
import net.rickiekarp.loginserver.dto.TokenDTO
import net.rickiekarp.loginserver.factory.AppObjectBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("account")
class AccountApi {

    @RequestMapping(
            value = ["login"],
            method = arrayOf(RequestMethod.POST)
    )
    fun login(): ResponseEntity<AppObjectDTO> {
        return try {
            val dto = AppObjectDTO(AppObjectBuilder())
            dto.serverVersion = net.rickiekarp.foundation.config.ServerContext.serverVersion
            dto.setFeatureSettings(net.rickiekarp.foundation.config.Configuration.properties)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(null, HttpStatus.UNAUTHORIZED)
        }
    }

    @RequestMapping(
            value = ["create"],
            method = arrayOf(RequestMethod.POST)
    )
    fun create(credentials: Credentials): ResponseEntity<TokenDTO> {
        val user = net.rickiekarp.foundation.config.ServerContext.loginDao.registerUser(credentials)
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
