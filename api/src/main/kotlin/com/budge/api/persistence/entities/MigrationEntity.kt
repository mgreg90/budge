package com.budge.api.persistence.entities

import com.budge.api.persistence.tables.MigrationTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class MigrationEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    var timestamp by MigrationTable.timestamp
    var name by MigrationTable.name
    var executedAt by MigrationTable.executedAt

    companion object : UUIDEntityClass<MigrationEntity>(MigrationTable)
}