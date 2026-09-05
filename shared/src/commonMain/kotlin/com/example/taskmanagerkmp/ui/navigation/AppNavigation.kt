package com.example.taskmanagerkmp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskmanagerkmp.presentation.viewmodel.TaskViewModel
import com.example.taskmanagerkmp.ui.task.TaskDetailScreen
import com.example.taskmanagerkmp.ui.task.TaskFormScreen
import com.example.taskmanagerkmp.ui.task.TaskListScreen
import com.example.taskmanagerkmp.ui.task.components.DeleteConfirmationDialog
import org.koin.compose.koinInject


sealed class Screen(val route: String) {
    data object TaskList : Screen("task_list")
    data object TaskCreate : Screen("task_create")

    data class TaskDetail(val id: Long) : Screen("task_detail/{taskId}") {
        companion object {
            const val ROUTE = "task_detail/{taskId}"
            fun createRoute(id: Long) = "task_detail/$id"
        }
    }

    data class TaskEdit(val id: Long) : Screen("task_edit/{taskId}") {
        companion object {
            const val ROUTE = "task_edit/{taskId}"
            fun createRoute(id: Long) = "task_edit/$id"
        }
    }
}

@Composable
fun AppNavigation() {
    val viewModel = koinInject<TaskViewModel>()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.TaskList.route
    ) {
        composable(Screen.TaskList.route) {
            TaskListScreen(
                viewModel = viewModel,
                onTaskClick = { task ->
                    viewModel.getTaskById(task.id)
                    navController.navigate(Screen.TaskDetail.createRoute(task.id))
                },
                onAddClick = {
                    navController.navigate(Screen.TaskCreate.route)
                }
            )
        }

        composable(Screen.TaskCreate.route) {
            TaskFormScreen(
                taskToEdit = null,
                onSave = {
                    viewModel.createTask(it.title, it.description)
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() },
                onDelete = null
            )
        }

        composable(Screen.TaskDetail.ROUTE) { _ ->
            val uiState by viewModel.state.collectAsState()
            val task = uiState.selectedTask
            var showDeleteDialog by remember { mutableStateOf(false) }

            if (task != null) {
                TaskDetailScreen(
                    task = task,
                    onEdit = { navController.navigate(Screen.TaskEdit.createRoute(task.id)) },
                    onBack = { navController.popBackStack() },
                    showDeleteDialog = { showDeleteDialog = it }
                )

                if (showDeleteDialog) {
                    DeleteConfirmationDialog(
                        onConfirm = {
                            viewModel.deleteTask(task.id)
                            showDeleteDialog = false
                            navController.popBackStack()
                        },
                        onDismiss = { showDeleteDialog = false }
                    )
                }
            }
        }

        composable(Screen.TaskEdit.ROUTE) { _ ->
            val uiState by viewModel.state.collectAsState()
            val task = uiState.selectedTask
            TaskFormScreen(
                taskToEdit = task,
                onSave = { updated ->
                    viewModel.updateTask(updated)
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() },
                onDelete = { id ->
                    viewModel.deleteTask(id)
                    // pop edit + detail back to the list
                    navController.popBackStack(Screen.TaskList.route, inclusive = false)
                }
            )
        }
    }
}