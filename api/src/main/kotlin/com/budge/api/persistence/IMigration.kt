package com.budge.api.persistence

interface IMigration {
    val timestamp : Long

    fun up()
    fun down()
}