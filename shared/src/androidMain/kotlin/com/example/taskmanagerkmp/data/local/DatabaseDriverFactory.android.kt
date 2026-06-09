package com.example.taskmanagerkmp.data.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.taskmanagerkmp.shared.db.TaskDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver =
        AndroidSqliteDriver(
            schema = TaskDatabase.Schema,
            context = context,
            name = "tasks.db",
        )
}
