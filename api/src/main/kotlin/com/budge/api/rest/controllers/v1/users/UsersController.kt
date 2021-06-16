package com.budge.api.rest.controllers.v1.users

import com.budge.api.rest.controllers.BaseController
import com.budge.api.rest.controllers.IController
import com.budge.api.rest.controllers.v1.sessions.requestDtos.SessionCreateRequestDto
import com.budge.api.rest.controllers.v1.users.requestDtos.UserCreateRequestDto.DtoParser
import com.budge.api.rest.controllers.v1.users.responseDtos.UserCreateResponseDto
import com.budge.api.services.AuthService
import com.budge.api.services.UserService
import com.budge.api.utils.Either
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.http.Context

class UsersController(
    private val userService : UserService,
    private val authService : AuthService
) : BaseController(), IController {
    override fun routes() {
        path("/api/v1/users") {
            post(::create)
        }
    }

    private fun create(ctx: Context) {
        controllerAction(ctx) {
            // Parse and validate request
            val userCreateDto = when(val validationResult = DtoParser().parse(ctx)) {
                is Either.Problem -> {
                    validationResult.problem.renderJson(ctx)
                    return@controllerAction
                }
                is Either.Value -> validationResult.value
            }

            // Create user
            val user = when (val creationResult = userService.create(userCreateDto)) {
                is Either.Problem -> {
                    creationResult.problem.renderJson(ctx)
                    return@controllerAction
                }
                is Either.Value -> creationResult.value
            }

            // Create session
//            val sessionCreateDto = userCreateDto
            val session = when (val creationResult = authService.login(userCreateDto.toSessionDto())) {
                is Either.Problem -> {
                    creationResult.problem.renderJson(ctx)
                    return@controllerAction
                }
                is Either.Value -> creationResult.value
            }

            ctx.json(UserCreateResponseDto(user, session.token))
        }
    }

}