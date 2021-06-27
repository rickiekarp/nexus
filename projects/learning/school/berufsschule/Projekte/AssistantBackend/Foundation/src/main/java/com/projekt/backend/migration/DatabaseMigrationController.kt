package com.projekt.backend.migration

import org.flywaydb.core.Flyway
import org.flywaydb.core.api.MigrationVersion
import javax.sql.DataSource

class DatabaseMigrationController {

    fun migrateDatabase(dataSource: DataSource) {
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