package com.budge.api.clients

import org.slf4j.LoggerFactory

abstract class BaseClient {
    protected val logger = LoggerFactory.getLogger(javaClass)
}