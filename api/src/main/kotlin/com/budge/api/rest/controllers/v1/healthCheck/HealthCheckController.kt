package com.budge.api.rest.controllers.v1.healthCheck

import com.budge.api.domain.statuses.HealthCheckStatus
import com.budge.api.rest.controllers.IController
import com.budge.api.rest.controllers.v1.healthCheck.responseDtos.ReadResponseDto
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.http.Context

class HealthCheckController: IController {
    override fun routes() {
        path("/api/v1/health-check") {
            get(::read)
        }
    }

    private fun read(ctx: Context) {
        val response = ReadResponseDto (
            serverStatus = HealthCheckStatus.ACTIVE
        )
        ctx.json(response)
    }
}