package com.compose.todolist.data.network

import com.compose.todolist.data.models.CreateTodoRequest
import com.compose.todolist.data.models.Todo
import com.compose.todolist.data.models.UpdateTodoRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoAPI {
    @GET("todos/")
    suspend fun getTodos(): List<Todo>

    @POST("todos/")
    suspend fun createTodo(@Body todo: CreateTodoRequest): Todo

    @PUT("todos/{id}")
    suspend fun updateTodo(
        @Path("id") id: Int,
        @Body todo: UpdateTodoRequest
    ): Todo

    @DELETE("todos/{id}")
    suspend fun deleteTodo(@Path("id") id: Int)
}