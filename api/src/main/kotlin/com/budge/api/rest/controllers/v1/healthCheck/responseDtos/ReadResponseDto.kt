package com.budge.api.rest.controllers.v1.healthCheck.responseDtos

import com.budge.api.domain.statuses.HealthCheckStatus

class ReadResponseDto(
    val serverStatus: HealthCheckStatus
)