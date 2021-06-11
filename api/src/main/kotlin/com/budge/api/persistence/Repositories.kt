package com.budge.api.persistence

import com.budge.api.persistence.repositories.UserRepository

object Repositories {
    var all = listOf<IRepository>()

    var userRepository = UserRepository

    fun init(): Repositories {
        all = listOf(
            userRepository
        )

        return this
    }

    fun registerIndexes() : Repositories {
        all.forEach { it.registerIndexes() }
        return this
    }
}