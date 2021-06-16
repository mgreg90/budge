package com.budge.api.services

import com.budge.api.EnvVars
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

        val now = Date()
        val expirationTime = Calendar.getInstance().let { cal -> {
            cal.time = now
            cal.add(Calendar.DATE, DEFAULT_TOKEN_EXPIRATION_DAYS);
            cal.time
        } }

        // Create user token
        val jwt = Jwts.builder()
            .setId(UUID.randomUUID().toString())
            .setIssuer(TOKEN_ISSUER)
            .setSubject(user.id.toString())
            .setAudience(user.id.toString())
            .setNotBefore(now)
            .setIssuedAt(now)
            .setExpiration(expirationTime())
            .claim(USER_ID_CLAIM_KEY, user.id)
            .claim(EMAIL_CLAIM_KEY, user.email)

        val signedJwt = jwt.signWith(signingKey()).compact()
        return Either.Value(Session(user, signedJwt))
    }

    fun validateToken(token: String, userId: UUID? = null): Boolean {
        return try {
            val tokenBody: Claims = Jwts.parserBuilder()
                .setSigningKey(signingKey())
                .build()
                .parse(token).body as Claims

            var condition = tokenBody.issuer == TOKEN_ISSUER &&
                    tokenBody.expiration > Date.from(Instant.now())
            if (userId != null) condition = condition && tokenBody[USER_ID_CLAIM_KEY] == userId.toString()

            condition
        } catch (ex: SignatureException) {
            false
        }
    }

    private fun signingKey() = Keys.hmacShaKeyFor(EnvVars.jwtSigningKey().toByteArray())

    companion object {
        private const val DEFAULT_TOKEN_EXPIRATION_DAYS = 7
        private const val TOKEN_ISSUER = "com.budge"
        private const val USER_ID_CLAIM_KEY = "userId"
        private const val EMAIL_CLAIM_KEY = "email"
    }
}