package com.budge.api.services

import com.budge.api.clients.Clients
import com.budge.api.persistence.Repositories
import org.eclipse.jetty.server.Authentication

class Services(private val clients: Clients, private val repositories: Repositories) {
    lateinit var userService : UserService

    fun init(): Services {
        userService = UserService(repositories.userRepository)
        return this
    }
}