package com.budge.api.utils.extensions

import com.budge.api.utils.Either
import com.budge.api.utils.Problems
import io.javalin.http.Context
import java.util.*

const val regexVal = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}"
val UuidRegex = Regex(regexVal)

fun Context.parsePathId(key : String = "id") : Either<Problems.Problem, UUID> {
    val idStr = pathParam(key)
    if (UuidRegex matches idStr) return Either.Value(idStr.toUUIDSafe()!!)

    return Either.Problem(Problems.VALIDATION_ERROR("Failed to parse $key from url"))
}