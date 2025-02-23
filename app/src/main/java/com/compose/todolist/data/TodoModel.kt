package com.compose.todolist.data

data class Todo(
    val id: Int = 0,
    val title: String,
    val description: String,
    val status: String = "pending",
    val created_at: String
)

data class CreateTodoRequest(
    val title: String,
    val description: String
)

data class UpdateTodoRequest(
    val title: String,
    val description: String,
    val status: String
)