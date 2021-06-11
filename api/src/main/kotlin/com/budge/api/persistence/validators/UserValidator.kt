package com.budge.api.persistence.validators

import com.budge.api.persistence.models.UserModel
import com.budge.api.persistence.repositories.UserRepository
import com.budge.api.rest.dtos.ValidationResult
import com.budge.api.utils.Either
import com.budge.api.utils.Problems

class UserValidator(private val model: UserModel, private val repository: UserRepository) {
    fun validate() : Either<Problems.Problem, UserModel> {
        val isEmailUnique = when(repository.findByEmail(model.email)) {
            is Either.Value -> false
            is Either.Problem -> true
        }

        if (!isEmailUnique) return Either.Problem(Problems.VALIDATION_ERROR(listOf(
            ValidationResult("email", "must be unique")
        )))

        return Either.Value(model)
    }
}