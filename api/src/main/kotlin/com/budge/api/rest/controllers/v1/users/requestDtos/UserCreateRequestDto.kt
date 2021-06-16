package com.budge.api.rest.controllers.v1.users.requestDtos

import com.budge.api.rest.controllers.v1.sessions.requestDtos.SessionCreateRequestDto
import com.budge.api.rest.dtos.DtoParserBase
import com.budge.api.rest.dtos.IDto
import com.budge.api.rest.dtos.IRawDto
import io.javalin.http.Context
import org.valiktor.Validator
import org.valiktor.functions.*

class UserCreateRequestDto(val email : String, val password : String) : IDto {
    constructor(rawDto : RawDto) : this(rawDto.email!!, rawDto.password!!)

    fun toSessionDto() = SessionCreateRequestDto(email, password)

    class RawDto(val email : String?, val password : String?, private val passwordConfirmation: String?) : IRawDto {
        override fun validations(validator : Validator<IRawDto>) {
            with(validator as Validator<RawDto>) {
                validate(RawDto::email).isNotNull().isNotBlank().hasSize(max = 320).isEmail()
                validate(RawDto::password).isNotNull().isNotBlank().hasSize(max = 25, min = 5)
                validate(RawDto::passwordConfirmation).isNotNull().isNotBlank().hasSize(max = 25, min = 5).isEqualTo(password)
            }
        }
    }

    class DtoParser() : DtoParserBase() {
        fun parse(ctx : Context) = _parse(ctx, ::UserCreateRequestDto)
    }
}
