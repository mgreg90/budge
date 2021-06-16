package com.budge.api.persistence.tables

import org.jetbrains.exposed.dao.id.UUIDTable

object UserTable : UUIDTable("users") {
    val email = varchar("email", 320).index().uniqueIndex()
    val passwordHash = varchar("passwordHash", 150)
}