package com.budge.api.persistence.entities

import com.budge.api.persistence.tables.UserTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    var email by UserTable.email
    var passwordHash by UserTable.passwordHash

    companion object : UUIDEntityClass<UserEntity>(UserTable)
}