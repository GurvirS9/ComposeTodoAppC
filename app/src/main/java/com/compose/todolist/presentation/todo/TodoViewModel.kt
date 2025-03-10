package com.compose.todolist.presentation.todo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.todolist.data.models.CreateTodoRequest
import com.compose.todolist.data.models.Todo
import com.compose.todolist.data.network.TodoService
import com.compose.todolist.data.models.UpdateTodoRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

private const val TAG = "TodoViewModel"

class TodoViewModel(private val todoService: TodoService) : ViewModel() {
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadTodos()
    }

    private fun handleError(e: Exception, operation: String) {
        val errorMessage = when (e) {
            is HttpException -> {
                when (e.code()) {
                    401 -> "Unauthorized"
                    403 -> "Forbidden"
                    404 -> "Not found"
                    else -> "Server error (${e.code()})"
                }
            }
            is UnknownHostException -> "Network error - Check your connection"
            is SocketTimeoutException -> "Connection timed out"
            else -> "An unexpected error occurred"
        }

        Log.e(TAG, "Error during $operation", e)
        _error.value = errorMessage
    }

    fun loadTodos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _todos.value = todoService.todoAPI.getTodos()
                _error.value = null
            } catch (e: Exception) {
                handleError(e, "loading todos")
                _todos.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createTodo(title: String, description: String) {
        viewModelScope.launch {
            try {
                todoService.todoAPI.createTodo(CreateTodoRequest(title, description))
                loadTodos()
                _error.value = null
            } catch (e: Exception) {
                handleError(e, "creating todo")
            }
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            try {
                todoService.todoAPI.updateTodo(
                    todo.id,
                    UpdateTodoRequest(todo.title, todo.description, todo.status)
                )
                loadTodos()
                _error.value = null
            } catch (e: Exception) {
                handleError(e, "updating todo")
            }
        }
    }

    fun deleteTodo(id: Int) {
        viewModelScope.launch {
            try {
                todoService.todoAPI.deleteTodo(id)
                loadTodos()
                _error.value = null
            } catch (e: Exception) {
                handleError(e, "deleting todo")
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}