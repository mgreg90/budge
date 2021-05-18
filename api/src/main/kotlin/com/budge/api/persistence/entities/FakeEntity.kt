package com.budge.api.persistence.entities

import com.budge.api.persistence.tables.FakeTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class FakeEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    var name by FakeTable.name
    var age by FakeTable.age

    companion object : UUIDEntityClass<FakeEntity>(FakeTable)
}