package com.budge.api.rest.controllers

import com.budge.api.domain.auth.JwtToken
import io.javalin.http.Context

import java.util.*

object ContextExtensions {
    private const val AUTHORIZATION_HEADER = "Authorization"

    fun Context.authToken(): String? {
        val header = this.header(AUTHORIZATION_HEADER) ?: return null
        return header.replace("Bearer ", "")
    }

    fun Context.userId(): UUID? =
        authToken()?.let { JwtToken(authToken()!!).userId() }
}