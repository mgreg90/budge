package com.budge.api.persistence.repositories

import com.budge.api.persistence.IRepository
import com.budge.api.persistence.entities.UserEntity
import com.budge.api.persistence.models.UserModel
import com.budge.api.persistence.tables.UserTable
import com.budge.api.utils.Either
import com.budge.api.utils.Problems
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.transactions.transaction

object UserRepository : IRepository {
    fun findByEmail(email: String) = handleTxn {
        val user = UserEntity.find { UserTable.email eq email }.firstOrNull()
            ?: return@handleTxn Either.Problem(Problems.NOT_FOUND())

        return@handleTxn Either.Value(UserModel(user))
    }

    fun create(userModel: UserModel) = handleTxn {
        Either.Value(UserModel(UserEntity.new {
            email = userModel.email
            passwordHash = userModel.passwordHash()
        }))
    }

    override fun registerIndexes() {

    }
}