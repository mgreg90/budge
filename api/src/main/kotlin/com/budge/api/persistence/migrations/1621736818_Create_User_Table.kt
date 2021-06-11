package com.budge.api.persistence.migrations

import com.budge.api.persistence.IMigration
import com.budge.api.persistence.tables.FakeTable
import com.budge.api.persistence.tables.UserTable
import org.jetbrains.exposed.sql.SchemaUtils

class `1621736818_Create_User_Table` : IMigration {
    override val timestamp = 1621736818L

    override fun up() {
        SchemaUtils.create(UserTable)
    }

    override fun down() {
        SchemaUtils.drop(UserTable)
    }
}