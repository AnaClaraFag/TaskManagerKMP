package com.example.taskmanagerkmp

import org.koin.core.module.Module

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect val platformModule: Module

