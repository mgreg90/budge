package com.budge.api.rest.controllers

import com.budge.api.utils.Exceptions
import com.budge.api.rest.controllers.v1.healthCheck.HealthCheckController
import com.budge.api.rest.controllers.v1.sessions.SessionsController
import com.budge.api.rest.controllers.v1.transactions.TransactionsController
import com.budge.api.rest.controllers.v1.users.UsersController
import com.budge.api.services.Services
import io.javalin.Javalin

class Controllers(private val services : Services) {
    private var all = listOf<IController>()
    private var isInitialized = false

    fun init() : Controllers {
        // Initialize controllers, pass in needed services
        val healthCheckController = HealthCheckController()
        val sessionsController = SessionsController(services.authService)
        val transactionsController = TransactionsController()
        val usersController = UsersController(services.userService, services.authService)

        // List all controllers in alphabetical order
        all = listOf(
            healthCheckController,
            sessionsController,
            transactionsController,
            usersController
        )

        isInitialized = true

        return this
    }

    fun registerRoutes(app : Javalin) {
        if (!isInitialized) throw Exceptions.InitializationException(javaClass, "Cannot register routes before initializing controllers")
        app.routes {
            all.forEach { it.routes() }
        }
    }

    companion object {}
}