package com.compose.todolist.presentation.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.compose.todolist.data.network.TodoService

class TodoViewModelFactory(private val todoService: TodoService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(todoService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}