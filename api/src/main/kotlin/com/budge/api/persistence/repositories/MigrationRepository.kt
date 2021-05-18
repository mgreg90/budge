package com.budge.api.persistence.repositories

import com.budge.api.persistence.entities.MigrationEntity
import com.budge.api.persistence.models.MigrationModel
import com.budge.api.persistence.tables.MigrationTable
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.transaction

object MigrationRepository {
    fun index() = transaction {
        MigrationEntity
            .all()
            .orderBy(MigrationTable.timestamp to SortOrder.ASC)
            .map { MigrationModel(it) }
    }

    fun add(model: MigrationModel) = transaction {
        MigrationEntity.new {
            timestamp = model.timestamp.toInt()
            name = model.name
            executedAt = model.executedAt
        }
    }
}