package com.budge.api.rest.controllers.v1.sessions

import com.budge.api.rest.controllers.BaseController
import com.budge.api.rest.controllers.ContextExtensions.authToken
import com.budge.api.rest.controllers.IController
import com.budge.api.rest.controllers.v1.sessions.requestDtos.SessionCreateRequestDto
import com.budge.api.rest.controllers.v1.sessions.responseDtos.SessionCreateResponseDto
import com.budge.api.rest.controllers.v1.sessions.responseDtos.SessionReadResponseDto
import com.budge.api.services.AuthService
import com.budge.api.utils.Either
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.http.Context

class SessionsController(
    private val authService : AuthService
) : BaseController(), IController {
    override fun routes() {
        path("/api/v1/sessions") {
            post(::create)
            get(::read)
        }
    }

    private fun create(ctx: Context) {
        controllerAction(ctx) {
            val sessionCreateDto = when(val validationResult = SessionCreateRequestDto.DtoParser().parse(ctx)) {
                is Either.Problem -> {
                    validationResult.problem.renderJson(ctx)
                    return@controllerAction
                }
                is Either.Value -> validationResult.value
            }

            val session = when (val queryResult = authService.login(sessionCreateDto)) {
                is Either.Problem -> {
                    queryResult.problem.renderJson(ctx)
                    return@controllerAction
                }
                is Either.Value -> queryResult.value
            }

            ctx.json(SessionCreateResponseDto(session))
        }
    }

    private fun read(ctx: Context) {
        controllerAction(ctx) {
            val authToken = ctx.authToken()
            if (ctx.authToken().isNullOrBlank()) {
                ctx.json(SessionReadResponseDto(false))
                return@controllerAction
            }

            val isTokenValid = authToken?.let { authService.validateToken(it) } ?: false

            ctx.json(SessionReadResponseDto(isTokenValid))
        }
    }

}