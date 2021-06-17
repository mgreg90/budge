package com.budge.api.rest.controllers.v1.transactions

import com.budge.api.config.AccessManager
import com.budge.api.rest.controllers.BaseController
import com.budge.api.rest.controllers.IController
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.core.security.SecurityUtil.roles
import io.javalin.http.Context

class TransactionsController() : BaseController(), IController {
    override fun routes() {
        path("/api/v1/transactions") {
            get(::read, roles(AccessManager.Roles.REQUIRES_AUTH))
        }
    }

    private fun read(ctx: Context) {
        ctx.json(mapOf("message" to "This is private data!"))
    }

}