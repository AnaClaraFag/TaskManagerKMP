package com.example.taskmanagerkmp

import com.example.taskmanagerkmp.data.local.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual val platformModule: Module = module {
    single { DatabaseDriverFactory() }
}