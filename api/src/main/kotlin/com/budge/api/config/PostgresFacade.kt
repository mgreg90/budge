package com.budge.api.config

import com.budge.api.EnvVars
import com.budge.api.persistence.MigrationRunner
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

object PostgresFacade {
    const val DRIVER = "org.postgresql.Driver"

    fun connect() { Database.connect(hikariConfig()) }

    fun runMigrations() { MigrationRunner.run() }

    private fun hikariConfig() = HikariConfig().apply {
        driverClassName = DRIVER
        jdbcUrl = connectionString()
        if (!EnvVars.postgresUser().isNullOrBlank()) username = EnvVars.postgresUser()
        if (!EnvVars.postgresPassword().isNullOrBlank()) password = EnvVars.postgresPassword()
        validate()
    }.let { HikariDataSource(it) }

    private fun connectionString() : String {
        return "jdbc:postgresql://${EnvVars.postgresHost()}:${EnvVars.postgresPort()}/${EnvVars.postgresDbName()}"
    }
}