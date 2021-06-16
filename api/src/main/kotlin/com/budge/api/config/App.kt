package com.budge.api.config

import com.budge.api.EnvVars
import com.budge.api.clients.Clients
import com.budge.api.persistence.Repositories
import com.budge.api.rest.controllers.Controllers
import com.budge.api.services.Services
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.before
import io.javalin.core.util.Header
import org.slf4j.LoggerFactory

class App {
    private val logger = LoggerFactory.getLogger(javaClass)
    lateinit var app: Javalin
    lateinit var controllers: Controllers

    fun start() {
        readEnvVars()
        connectToDb()
        runMigrations()

        val clients = Clients.init()
        val repositories = Repositories.init().registerIndexes()
        val services = Services(clients, repositories).init()
        controllers = Controllers(services).init()

        app = Javalin.create {
            it.enableCorsForAllOrigins()
            it.enableDevLogging()
//            before { ctx ->
//                ctx.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
//            }
        }

        registerRoutes()
        startServer()
    }

    private fun readEnvVars() {
        logger.info("Reading Env Vars... ")
        EnvVars.init()
        logger.info("Reading Env Vars Complete!")
    }

    private fun connectToDb() {
        logger.info("Creating Database Connection... ")
        PostgresFacade.connect()
        logger.info("Creating Database Connection Complete!")
    }

    private fun runMigrations() {
        logger.info("Running Database Migrations... ")
        PostgresFacade.runMigrations()
        logger.info("Running Database Migrations Complete!")
    }

    private fun registerRoutes() {
        logger.info("Registering Routes... ")
        controllers.registerRoutes(app)
        logger.info("Registering Routes Complete!")
    }

    private fun startServer() {
        val port = EnvVars.port()

        logger.info("Starting server on port ${port}... ")
        app.start(port)
        logger.info("Server is live on port ${port}! 🚀")
    }
}