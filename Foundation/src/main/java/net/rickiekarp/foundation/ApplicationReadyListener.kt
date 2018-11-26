package net.rickiekarp.foundation

import net.rickiekarp.foundation.config.database.DataSourceFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

import javax.sql.DataSource

@Component
class ApplicationReadyListener : ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    internal var dataSource: DataSource? = null

    @Autowired
    internal var servlet: ApplicationContext? = null

    @Value("\${spring.application.name}")
    private val appName: String? = null

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        println("startup")
        //        loadConfiguration();

        // Set up server context and load settings
//        ServerContext.loadDatabaseProperties(this.javaClass)

        net.rickiekarp.foundation.config.database.DataSourceFactory(appName!!)

//        val databaseMigrationController = DatabaseMigrationController()
//        databaseMigrationController.migrateDatabase(appName, dataSource!!)

//        parseServerManifest(servlet!!)
    }
}