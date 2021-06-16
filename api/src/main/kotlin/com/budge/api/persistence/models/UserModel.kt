package com.budge.api.persistence.models

import com.budge.api.persistence.entities.UserEntity
import com.budge.api.persistence.repositories.UserRepository
import com.budge.api.persistence.tables.UserTable
import com.budge.api.rest.controllers.v1.users.requestDtos.UserCreateRequestDto
import com.budge.api.rest.dtos.ValidationResult
import com.budge.api.utils.Either
import com.budge.api.utils.Problems
import org.mindrot.jbcrypt.BCrypt
import java.util.*

class UserModel(
    val email : String,
    private val password : String? = null,
    private var passwordHash : String? = null,
    val id : UUID? = null
) {
    constructor(userCreateRequestDto: UserCreateRequestDto) : this(
        email = userCreateRequestDto.email,
        password = userCreateRequestDto.password
    )

    constructor(userEntity: UserEntity) : this(
        id = userEntity.id.value,
        email = userEntity.email,
        passwordHash = userEntity.passwordHash
    )

    fun passwordHash() : String {
        if (passwordHash != null) return passwordHash!!
        passwordHash = BCrypt.hashpw(this.password!!, BCrypt.gensalt())
        return passwordHash!!
    }

    fun verifyPassword(attemptedPw: String) = BCrypt.checkpw(attemptedPw, passwordHash())
}