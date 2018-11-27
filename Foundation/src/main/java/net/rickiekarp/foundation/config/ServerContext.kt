package net.rickiekarp.foundation.config

object ServerContext {
    var serverVersion: String? = null
    var developerEnvironment: Boolean = false

    init {
        println("init:${ServerContext}")
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