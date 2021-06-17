package com.budge.api.domain.auth

import com.budge.api.EnvVars
import com.budge.api.persistence.models.UserModel
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.util.*

class JwtToken(private val token: String) {
    private var _parsed: Claims? = null

    fun userId(): UUID? = (parsed()[USER_ID_CLAIM_KEY] as String?).let { UUID.fromString(it) }

    fun issuer(): String = parsed().issuer

    fun expiration(): Date = parsed().expiration

    private fun parsed(): Claims {
        if (_parsed != null) return _parsed as Claims
        _parsed = Jwts.parserBuilder()
            .setSigningKey(signingKey())
            .build()
            .parse(token).body as Claims
        return _parsed as Claims
    }
    companion object {
        const val USER_ID_CLAIM_KEY = "userId"
        const val EMAIL_CLAIM_KEY = "email"
        const val TOKEN_ISSUER = "com.budge"

        private const val DEFAULT_TOKEN_EXPIRATION_DAYS = 7
        private fun signingKey() = Keys.hmacShaKeyFor(EnvVars.jwtSigningKey().toByteArray())

        fun create(user: UserModel): String {
            val now = Date()
            val expirationTime = Calendar.getInstance().let { cal -> {
                cal.time = now
                cal.add(Calendar.DATE, DEFAULT_TOKEN_EXPIRATION_DAYS);
                cal.time
            } }

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

            return jwt.signWith(signingKey()).compact()
        }
    }
}