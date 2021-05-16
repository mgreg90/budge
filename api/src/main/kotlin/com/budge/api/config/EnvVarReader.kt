package com.budge.api.config

import com.budge.api.utils.Exceptions
import io.github.cdimascio.dotenv.dotenv
import org.slf4j.LoggerFactory
import java.lang.NumberFormatException

class EnvVarReader {
    private val missingEnvVars = mutableListOf<String>()
    private val invalidEnvVars = mutableListOf<String>()
    private val env = dotenv()
    private val logger = LoggerFactory.getLogger(javaClass)

    fun readInt(varName : String) = try {
        read(varName)?.toInt() ?: 0
    } catch (e : NumberFormatException) {
        invalidEnvVars.add(varName)
        0
    }

    fun readStr(varName : String) = read(varName) ?: ""

    fun validate() {
        if (missingEnvVars.any() || invalidEnvVars.any())
            throw Exceptions.EnvVarException(missingEnvVars = missingEnvVars, invalidEnvVars = invalidEnvVars)
    }

    private fun read(varName : String) : String? {
        val value = env.get(varName)
        if (value != null) return value
        missingEnvVars.add(varName)
        return null
    }
}