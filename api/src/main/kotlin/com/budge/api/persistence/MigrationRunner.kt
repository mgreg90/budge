package com.budge.api.persistence

import com.budge.api.persistence.repositories.MigrationRepository
import com.budge.api.persistence.tables.MigrationTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.reflections.Reflections
import org.slf4j.LoggerFactory

object MigrationRunner {
    private val logger = LoggerFactory.getLogger(javaClass)
    const val TIMESTAMP = "timestamp"

    fun run() {
        // Get current time
        val now = currentTimestamp()
        // Get all migration classes
        val migrationClasses = migrationClasses()
        // Get all migrations from database in order
        val previouslyRunMigrations = findOrCreateMigrationTable()
        // create list of migrations that (a) have not been run and (b) are timestamped >= now
        // TODO
//        val unexecutedMigrations = migrationClasses.filter {
////            if (previouslyRunMigrations.any { previousMigration -> previousMigration}) return@filter false
//
////            val timestampField = it.declaredFields.find { it.name == TIMESTAMP }
////            return timestampField
////            val timestampField = it.declaredFields.find {
////                return it.name == TIMESTAMP
////            }
////            return@filter true
//        }
        // Log all unrun migrations
        // For each unrun migration
            // attempt to run `up`
            // if it fails, run `down` and fail
            // if that fails, fail
    }

    fun resetTo(version: Long) {
        // get all migrations from database in order greater than `version`
        // For each migration
            // run `down`
            // if it fails, fail
    }

    private fun currentTimestamp() = System.currentTimeMillis() / 1000

    private fun migrationClasses() =
        Reflections("com.budge").getSubTypesOf(IMigration::class.java)

    private fun findOrCreateMigrationTable() : List<ResultRow> {
        var results : List<ResultRow>
        try {
            results = MigrationRepository.index()
            print(results)
        } catch (ex : Exception) {
            logger.info("Migrations Table Does Not Exist! Creating...")
            transaction { SchemaUtils.create(MigrationTable) }
            results = MigrationRepository.index()
            logger.info("Migrations Table Created!")
        }
        return results
    }
}