package com.budge.api.rest.controllers.v1.sessions.requestDtos

import com.budge.api.rest.controllers.v1.users.requestDtos.UserCreateRequestDto
import com.budge.api.rest.dtos.DtoParserBase
import com.budge.api.rest.dtos.IDto
import com.budge.api.rest.dtos.IRawDto
import io.javalin.http.Context
import org.valiktor.Validator
import org.valiktor.functions.*

class SessionCreateRequestDto(val email : String, val password : String) : IDto {
    constructor(rawDto : RawDto) : this(rawDto.email!!, rawDto.password!!)

    class RawDto(val email : String?, val password : String?) : IRawDto {
        override fun validations(validator : Validator<IRawDto>) {
            with(validator as Validator<RawDto>) {
                validate(RawDto::email).isNotNull().isNotBlank().hasSize(max = 320).isEmail()
                validate(RawDto::password).isNotNull().isNotBlank().hasSize(max = 25, min = 5)
            }
        }
    }

    class DtoParser() : DtoParserBase() {
        fun parse(ctx : Context) = _parse(ctx, ::SessionCreateRequestDto)
    }
}
