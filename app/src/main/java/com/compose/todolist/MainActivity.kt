package com.compose.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.compose.todolist.data.TodoService
import com.compose.todolist.presentation.SettingsScreen
import com.compose.todolist.presentation.SettingsViewModel
import com.compose.todolist.presentation.TodoScreen
import com.compose.todolist.presentation.TodoViewModel
import com.compose.todolist.presentation.TodoViewModelFactory
import com.compose.todolist.ui.theme.TodoTheme

class MainActivity : ComponentActivity() {
    private lateinit var todoService: TodoService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoService = TodoService(applicationContext)

        setContent {
            val settingsViewModel: SettingsViewModel = viewModel()
            val settings by settingsViewModel.settings.collectAsState()
            val navController = rememberNavController()

            TodoTheme(settings = settings) {
                NavHost(navController = navController, startDestination = "todo") {
                    composable("todo") {
                        val todoViewModel: TodoViewModel = viewModel(
                            factory = TodoViewModelFactory(todoService)
                        )
                        TodoScreen(
                            onNavigateToSettings = {
                                navController.navigate("settings")
                            },
                            viewModel = todoViewModel
                        )
                    }
                    composable("settings") {
                        SettingsScreen(
                            onNavigateBack = {
                                navController.popBackStack()
                            },
                            viewModel = settingsViewModel
                        )
                    }
                }
            }
        }
    }
}