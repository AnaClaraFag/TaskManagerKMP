package com.example.taskmanagerkmp

import android.app.Application
import com.example.taskmanagerkmp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class TaskManagerApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TaskManagerApp)
            modules(platformModule, appModule)
        }
    }
}