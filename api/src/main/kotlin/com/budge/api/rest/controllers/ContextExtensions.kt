package com.budge.api.rest.controllers

import io.javalin.http.Context

object ContextExtensions {
    private const val AUTHORIZATION_HEADER = "Authorization"

    fun Context.authToken(): String? {
        val header = this.header(AUTHORIZATION_HEADER) ?: return null
        return header.replace("Bearer ", "")
    }
}