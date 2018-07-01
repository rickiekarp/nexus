package com.rkarp.foundation.config

import com.rkarp.foundation.dao.UserDAO
import com.rkarp.foundation.dao.UserDaoImpl

object ServerContext {
    var serverVersion: String? = null
    val loginDao: UserDAO
    var developerEnvironment: Boolean = false

    init {
        loginDao = UserDaoImpl()
    }

    fun getHomeDirPath(clazz: Class<*>): String {
        var path = System.getProperty("user.dir")
        if (path.contains("tomcat")) {
            path += "/webapps/" + clazz.simpleName
        } else {
            path += "/WebApp"
        }
        return path
    }
}