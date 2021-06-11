package com.budge.api.persistence

import com.budge.api.persistence.entities.UserEntity
import com.budge.api.persistence.models.UserModel
import com.budge.api.utils.Either
import com.budge.api.utils.Problems
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

interface IRepository {
    fun registerIndexes()

    fun <T>handleTxn(fn: () -> Either<Problems.Problem, T>) = try {
        transaction {
            return@transaction fn()
        }
    } catch (exception: ExposedSQLException) {
        Either.Problem(Problems.DATABASE_TRANSACTION_FAILED_ERROR())
    }
}