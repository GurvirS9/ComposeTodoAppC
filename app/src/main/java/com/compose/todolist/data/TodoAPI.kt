package com.compose.todolist.data

import retrofit2.http.*

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