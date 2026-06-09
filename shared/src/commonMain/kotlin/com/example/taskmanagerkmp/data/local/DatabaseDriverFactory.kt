package com.example.taskmanagerkmp.data.local

import app.cash.sqldelight.db.SqlDriver
import com.example.taskmanagerkmp.shared.db.TaskDatabase

/**
 * Platform-specific factory responsible for creating the [SqlDriver] backing the database.
 *
 * Each target provides its own [actual] implementation (Android needs a [android.content.Context],
 * iOS does not), keeping the rest of the code platform-agnostic.
 */
expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

/**
 * Builds the SQLDelight [TaskDatabase] from a platform driver.
 */
fun createDatabase(driverFactory: DatabaseDriverFactory): TaskDatabase =
    TaskDatabase(driverFactory.createDriver())
