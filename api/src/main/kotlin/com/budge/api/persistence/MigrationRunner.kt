package com.budge.api.persistence

import com.budge.api.persistence.models.MigrationModel
import com.budge.api.persistence.repositories.MigrationRepository
import com.budge.api.persistence.tables.MigrationTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.reflections.Reflections
import org.slf4j.LoggerFactory

object MigrationRunner {
    private val logger = LoggerFactory.getLogger(javaClass)
    const val TIMESTAMP = "timestamp"

    fun run() {
        val unexecutedMigrations = migrationClasses().filter {
            val model = previouslyRunMigrations().find { migration -> migration.timestamp == it.timestamp }
            return@filter (it.isInThePast() && model == null)
        }

        logger.info("The following migrations have not been run: ${unexecutedMigrations.map { "${it.javaClass}, " }}")

        unexecutedMigrations.forEach {
            try {
                transaction {
                    it.up()
                    MigrationRepository.add(it.toMigrationModel())
                }
            } catch (e : Exception) {
                logger.error("Migration ${it.javaClass} failed! Rolling back...")
                logError(e)
                try {
                    transaction { it.down() }
                    logger.info("Rollback successful!")
                    throw e
                } catch (e : Exception) {
                    logger.error("Rollback for ${it.javaClass} failed! Manual inspection needed!")
                    logError(e)
                    throw e
                }
            }
        }
    }

    fun resetTo(version: Long) {
        // TODO
        // get all migrations from database in order greater than `version`
        // For each migration
            // run `down`
            // if it fails, fail
    }

    private fun migrationClasses() =
        Reflections("com.budge").getSubTypesOf(IMigration::class.java).map {
            it.constructors[0].newInstance() as IMigration
        }

    private fun previouslyRunMigrations() : List<MigrationModel> {
        transaction { SchemaUtils.create(MigrationTable) }
        return MigrationRepository.index()
    }

    private fun logError(e : Exception) {
        logger.error("${ e.javaClass }")
        logger.error(e.message)
        logger.error(e.stackTrace.joinToString("\n"))
    }
}