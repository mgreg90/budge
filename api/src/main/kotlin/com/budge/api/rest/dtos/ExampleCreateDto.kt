package com.budge.api.rest.dtos

import io.javalin.http.Context
import org.valiktor.Validator
import org.valiktor.functions.isGreaterThan
import org.valiktor.functions.isNotBlank
import org.valiktor.functions.isNotNull

class ExampleCreateDto(val name : String, val age : Int) : IDto {
    constructor(rawDto : RawExampleCreateDto) : this(rawDto.name!!, rawDto.age!!)
}

class RawExampleCreateDto(val name : String?, val age : Int?) : IRawDto {
    override fun validations(validator : Validator<IRawDto>) {
        with(validator as Validator<RawExampleCreateDto>) {
            validate(RawExampleCreateDto::name).isNotNull().isNotBlank()
            validate(RawExampleCreateDto::age).isNotNull().isGreaterThan(0)
        }
    }
}

class ExampleCreateDtoParser() : DtoParserBase() {
    fun parse(ctx : Context) = _parse(ctx, ::ExampleCreateDto)
}
