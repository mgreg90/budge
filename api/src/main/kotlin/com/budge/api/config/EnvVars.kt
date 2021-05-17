package com.budge.api

import com.budge.api.config.EnvVarReader
import com.budge.api.utils.Exceptions
import io.github.cdimascio.dotenv.dotenv
import org.slf4j.LoggerFactory
import java.lang.NumberFormatException

object EnvVars {
    private var isInitialized = false
    private var port : Int = 0
    private var postgresUser : String? = null
    private var postgresPassword : String? = null
    private lateinit var postgresDbName : String
    private lateinit var postgresPort : String
    private lateinit var postgresHost : String

    fun init() {
        val envReader = EnvVarReader()
        port = envReader.readInt("PORT")
        postgresUser = envReader.readNullableStr("POSTGRES_USER")
        postgresPassword = envReader.readNullableStr("POSTGRES_PASSWORD")
        postgresDbName = envReader.readStr("POSTGRES_DB_NAME")
        postgresPort = envReader.readStr("POSTGRES_PORT")
        postgresHost = envReader.readStr("POSTGRES_HOST")

        envReader.validate()
        isInitialized = true
    }

    fun port() = get(port)
    fun postgresUser() = get(postgresUser)
    fun postgresPassword() = get(postgresPassword)
    fun postgresDbName() = get(postgresDbName)
    fun postgresPort() = get(postgresPort)
    fun postgresHost() = get(postgresHost)

    private fun <T : Any?>get(variable : T) : T {
        if (!isInitialized) throw Exceptions.InitializationException(javaClass, "EnvVars are not yet initialized!")
        return variable
    }
}