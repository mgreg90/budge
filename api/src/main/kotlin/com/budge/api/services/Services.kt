package com.budge.api.services

import com.budge.api.clients.Clients
import com.budge.api.persistence.Repositories

class Services(private val clients: Clients, private val repositories: Repositories) {
//    lateinit var someService : SomeService


    fun init(): Services {
//        someService = SomeService(clients.someClient, repositories.someRepository)
        return this
    }
}