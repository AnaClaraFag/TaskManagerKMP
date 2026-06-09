package com.example.taskmanagerkmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.taskmanagerkmp.ui.navigation.AppNavigation
import com.example.taskmanagerkmp.ui.theme.TaskManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagerTheme {
                AppNavigation()
            }
        }
    }
}