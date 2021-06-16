package com.budge.api.services

import com.budge.api.persistence.models.UserModel
import com.budge.api.persistence.repositories.UserRepository
import com.budge.api.persistence.validators.UserValidator
import com.budge.api.rest.controllers.v1.users.requestDtos.UserCreateRequestDto
import com.budge.api.utils.Either
import com.budge.api.utils.Problems

class UserService(private val userRepository: UserRepository) {

    fun create(personDto: UserCreateRequestDto) : Either<Problems.Problem, UserModel> {
        val model = when(val result = UserValidator(UserModel(personDto), userRepository).validate()) {
            is Either.Problem -> return result
            is Either.Value -> result.value
        }

        return when(val result = userRepository.create(model)) {
            is Either.Problem -> result
            is Either.Value -> Either.Value(result.value)
        }
    }
}