package com.rkarp.foundation.config.database

import org.flywaydb.core.Flyway
import org.flywaydb.core.api.MigrationVersion
import org.flywaydb.core.internal.exception.FlywaySqlException
import org.springframework.context.ApplicationContext
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import javax.sql.DataSource

class DatabaseMigrationController {

    fun migrateDatabase(applicationName: String, dataSource: DataSource) {

        val scheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        var scheduledFuture: ScheduledFuture<Boolean>
        for (i in 1..20) {
            scheduledFuture = scheduledExecutorService.schedule(Callable {
                try {
                    doMigrate(dataSource)
                    true
                } catch (e: FlywaySqlException) {
                    println("There was an error when migrating $applicationName database! ($i)")
                    e.printStackTrace()
                    false
                }

            },2, TimeUnit.SECONDS)

            if (scheduledFuture.get()) {
                println("$applicationName migration complete!")
                scheduledExecutorService.shutdown()
                break
            }
        }
    }

    @Deprecated("REMOVE")
    fun migrateDatabase(servletContext: ApplicationContext) {
        val dataSource = DataSourceFactory.getDataSourceFromContext(servletContext)

        val scheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        var scheduledFuture: ScheduledFuture<Boolean>
        for (i in 1..20) {
            scheduledFuture = scheduledExecutorService.schedule(Callable {
                try {
                    doMigrate(dataSource)
                    true
                } catch (e: FlywaySqlException) {
                    println("There was an error when migrating ${servletContext.id} database! ($i)")
                    e.printStackTrace()
                    false
                }

            },2, TimeUnit.SECONDS)

            if (scheduledFuture.get()) {
                println("${servletContext.id} migration complete!")
                scheduledExecutorService.shutdown()
                break
            }
        }
    }

    @Throws(FlywaySqlException::class)
    private fun doMigrate(dataSource: DataSource) {
        val flyway = Flyway()
        flyway.dataSource = dataSource
        flyway.setLocations("db/migration")

        /*
         * Find which versions are not applied yet.
         */
        val migrationInfoService = flyway.info()
        val migrationInfo = migrationInfoService.all()

        for (mi in migrationInfo) {
            val version = mi.version.toString()
            val isMigrationApplied = mi.state.isApplied
            println(String.format("Is target version %s applied? %s", version, isMigrationApplied))

            // apply database migration
            if (!isMigrationApplied) {
                val migrationVersion = MigrationVersion.fromVersion(version)
                flyway.target = migrationVersion
                flyway.migrate() // Migrate up to version set in setTarget().
            }

        }
    }
}