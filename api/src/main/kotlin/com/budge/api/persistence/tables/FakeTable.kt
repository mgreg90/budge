package com.budge.api.persistence.tables

import org.jetbrains.exposed.dao.id.UUIDTable

object FakeTable : UUIDTable("fakes") {
    val name = varchar("name", 150)
    val age = integer("executedAt")
}