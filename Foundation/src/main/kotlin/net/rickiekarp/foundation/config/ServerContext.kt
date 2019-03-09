package net.rickiekarp.foundation.config

object ServerContext {
    lateinit var serverVersion: String
    var developerEnvironment: Boolean = false

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