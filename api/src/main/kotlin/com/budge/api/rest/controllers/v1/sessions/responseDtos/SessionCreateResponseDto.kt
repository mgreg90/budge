package com.budge.api.rest.controllers.v1.sessions.responseDtos

import com.budge.api.domain.auth.Session
import com.budge.api.persistence.models.UserModel
import java.util.*

data class SessionCreateResponseDto(val user : SessionCreateResponseUserDto, val token : String) {
    constructor(session: Session) : this (SessionCreateResponseUserDto(session.user), session.token)
}

data class SessionCreateResponseUserDto(val id : UUID, val email : String) {
    constructor(user: UserModel) : this(user.id!!, user.email)
}