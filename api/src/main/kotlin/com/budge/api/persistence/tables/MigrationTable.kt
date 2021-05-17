package com.budge.api.persistence.tables

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object MigrationTable : UUIDTable("migrations") {
    val timestamp = integer("timestamp")
    val name = varchar("name", 150)
    val executedAt = datetime("executedAt")
}