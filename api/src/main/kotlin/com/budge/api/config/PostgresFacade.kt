package com.budge.api.config

import com.budge.api.EnvVars
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

object PostgresFacade {
    const val DRIVER = "org.postgres.Driver"

    fun connect() {
//        Class.forName(DRIVER)
//        val connString = connectionString()
//        Database.connect(connectionString(), driver = DRIVER)
        Database.connect(hikariConfig())
    }

    private fun hikariConfig() = HikariConfig().apply {
        driverClassName = "org.postgresql.Driver"
        jdbcUrl = connectionString()
        if (!EnvVars.postgresUser().isNullOrBlank()) username = EnvVars.postgresUser()
        if (!EnvVars.postgresPassword().isNullOrBlank()) password = EnvVars.postgresPassword()
        validate()
    }.let { HikariDataSource(it) }

    private fun connectionString() : String {
        return "jdbc:postgresql://${EnvVars.postgresHost()}:${EnvVars.postgresPort()}/${EnvVars.postgresDbName()}"
    }
}