package net.rickiekarp.foundation.config

object ServerContext {
    var serverVersion: String? = null
    val loginDao: net.rickiekarp.foundation.dao.UserDAO
    var developerEnvironment: Boolean = false

    init {
        net.rickiekarp.foundation.config.ServerContext.loginDao = net.rickiekarp.foundation.dao.UserDaoImpl()
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