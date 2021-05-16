package com.budge.api.services

import org.slf4j.LoggerFactory

abstract class ServiceBase {
    protected val logger = LoggerFactory.getLogger(javaClass)
}