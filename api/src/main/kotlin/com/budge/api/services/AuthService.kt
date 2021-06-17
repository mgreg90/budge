package com.budge.api.services

import com.budge.api.EnvVars
import com.budge.api.domain.auth.JwtToken
import com.budge.api.domain.auth.Session
import com.budge.api.persistence.repositories.UserRepository
import com.budge.api.rest.controllers.v1.sessions.requestDtos.SessionCreateRequestDto
import com.budge.api.utils.Either
import com.budge.api.utils.Problems
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import java.time.Instant
import java.util.*

class AuthService(private val userRepo: UserRepository) {
    fun login(sessionDto: SessionCreateRequestDto) : Either<Problems.Problem, Session> {
        // Get user
        val user = when(val either = userRepo.findByEmail(sessionDto.email)) {
            is Either.Problem -> return Either.Problem(Problems.AUTHENTICATION_ERROR())
            is Either.Value -> either.value
        }

        // Verify user password
        if (!user.verifyPassword(sessionDto.password)) {
            return Either.Problem(Problems.AUTHENTICATION_ERROR())
        }

        val jwt = JwtToken.create(user)

        return Either.Value(Session(user, jwt))
    }

    fun validateToken(token: String, userId: UUID? = null): Boolean {
        return try {
            val jwtToken = JwtToken(token)

            var condition = jwtToken.issuer() == JwtToken.TOKEN_ISSUER &&
                    jwtToken.expiration() > Date.from(Instant.now())

            if (userId != null) condition = condition && jwtToken.userId() == userId

            condition
        } catch (ex: SignatureException) {
            false
        }
    }
}