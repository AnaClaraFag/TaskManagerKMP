package com.example.taskmanagerkmp.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.taskmanagerkmp.shared.db.TaskDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver =
        NativeSqliteDriver(
            schema = TaskDatabase.Schema,
            name = "tasks.db",
        )
}
