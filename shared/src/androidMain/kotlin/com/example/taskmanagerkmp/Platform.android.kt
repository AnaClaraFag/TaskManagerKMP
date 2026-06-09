package com.example.taskmanagerkmp

import android.os.Build
import com.example.taskmanagerkmp.data.local.DatabaseDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual val platformModule: Module = module {
    single { DatabaseDriverFactory(androidContext()) }
}