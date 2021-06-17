package com.budge.api.config

import com.budge.api.rest.controllers.ContextExtensions.authToken
import com.budge.api.services.AuthService
import com.budge.api.utils.Either
import com.budge.api.utils.Problems
import io.javalin.Javalin
import io.javalin.core.security.Role


class AccessManager(private val authService: AuthService) {
    internal enum class Roles : Role {
        PUBLIC, REQUIRES_AUTH
    }
    fun register(app: Javalin) {
        app.config.accessManager { handler, ctx, permittedRoles ->
            if(permittedRoles.contains(Roles.PUBLIC) || permittedRoles.none()) {
                handler.handle(ctx)
                return@accessManager
            }

            val token = ctx.authToken()
            if (token == null) {
                Problems.AUTHENTICATION_ERROR().renderJson(ctx)
                return@accessManager
            }

            val isValidToken = authService.validateToken(token)

            if(!isValidToken) {
                Problems.AUTHENTICATION_ERROR().renderJson(ctx)
                return@accessManager
            }

            handler.handle(ctx)
        }
    }
}