package com.budge.api.rest.controllers.v1.users.responseDtos

import com.budge.api.persistence.models.UserModel
import java.util.*

data class UserCreateResponseDto(val user : UserCreateResponseUserDto, val token : String) {
    constructor(user : UserModel, token : String) : this (UserCreateResponseUserDto(user.id!!, user.email), token)
}

data class UserCreateResponseUserDto(val id : UUID, val email : String)