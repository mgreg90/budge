package com.budge.api.rest.controllers

import com.budge.api.utils.Problems
import io.javalin.http.Context
import org.slf4j.LoggerFactory

abstract class BaseController {

    protected val logger = LoggerFactory.getLogger(javaClass)

    fun controllerAction(ctx : Context, func: () -> Unit) = try {
        func()
    } catch (e : Exception) {
        logger.error(e.message)
        Problems.INTERNAL_SERVER_ERROR().renderJson(ctx)
    }
}