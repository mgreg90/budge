package com.budge.api.clients

import com.budge.api.clients.someclient.SomeClient

object Clients {
    lateinit var someClient: SomeClient

    fun init(): Clients {
        someClient = SomeClient()
        return this
    }
}