package com.budge.api.persistence.migrations

import com.budge.api.persistence.IMigration
import com.budge.api.persistence.tables.FakeTable
import org.jetbrains.exposed.sql.SchemaUtils

class `1621210539_Create_Fake_Table` : IMigration {
    override val timestamp = 1621210539L

    override fun up() {
        SchemaUtils.create(FakeTable)
    }

    override fun down() {
        SchemaUtils.drop(FakeTable)
    }
}