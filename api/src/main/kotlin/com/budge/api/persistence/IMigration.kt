package com.budge.api.persistence

import com.budge.api.persistence.models.MigrationModel

interface IMigration {
    val timestamp : Long

    fun up()
    fun down()
    fun isInThePast() : Boolean {
        val currentTime = System.currentTimeMillis() / 1000
        return currentTime > timestamp
    }
    fun toMigrationModel() = MigrationModel(this)
}