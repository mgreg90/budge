package com.budge.api.persistence.repositories

import com.budge.api.persistence.tables.MigrationTable
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object MigrationRepository {
    fun index() = transaction {
        MigrationTable
            .selectAll()
            .orderBy(MigrationTable.timestamp to SortOrder.ASC)
            .map { it }
    }
}