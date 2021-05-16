package com.budge.api

import com.budge.api.config.EnvVarReader
import com.budge.api.utils.Exceptions
import io.github.cdimascio.dotenv.dotenv
import org.slf4j.LoggerFactory
import java.lang.NumberFormatException

object EnvVars {
    private var isInitialized = false
    private var port : Int = 0

    fun init() {
        val envReader = EnvVarReader()
        port = envReader.readInt("PORT")

        envReader.validate()
        isInitialized = true
    }

    fun port() = get(port)

    private fun <T : Any>get(variable : T) : T {
        if (!isInitialized) throw Exceptions.InitializationException(javaClass, "EnvVars are not yet initialized!")
        return variable
    }
}