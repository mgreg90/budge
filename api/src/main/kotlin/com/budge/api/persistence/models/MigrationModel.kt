package com.budge.api.persistence.models

import com.budge.api.persistence.IMigration
import com.budge.api.persistence.entities.MigrationEntity
import java.time.LocalDateTime
import java.util.*

class MigrationModel(
    val id: UUID,
    val timestamp: Long,
    val name: String,
    val executedAt: LocalDateTime
) {
    constructor(entity: MigrationEntity) : this(
        entity.id.value,
        entity.timestamp.toLong(),
        entity.name,
        entity.executedAt
    )
    constructor(migration: IMigration) : this(
        UUID.randomUUID(),
        migration.timestamp,
        migration.javaClass.toString(),
        LocalDateTime.now()
    )
}