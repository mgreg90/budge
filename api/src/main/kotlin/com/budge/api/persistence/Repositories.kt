package com.budge.api.persistence

object Repositories {
    var all = listOf<IRepository>()

//    lateinit var exampleRepository: ExampleRepository

    fun init(): Repositories {
//        exampleRepository = ExampleRepository()

        all = listOf(
//                exampleRepository
        )

        return this
    }

    fun registerIndexes() : Repositories {
        all.forEach { it.registerIndexes() }
        return this
    }
}