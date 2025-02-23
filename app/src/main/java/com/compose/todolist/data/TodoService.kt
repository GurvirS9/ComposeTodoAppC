package com.compose.todolist.data

import android.content.Context
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TodoService(context: Context) {
    private val settingsManager = SettingsManager(context)

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val apiKey = runBlocking {
                settingsManager.settingsData.first().apiKey
            }
            val request = chain.request().newBuilder()
                .addHeader("X-API-Key", apiKey)
                .build()
            chain.proceed(request)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://todocrud.chiggydoes.tech/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val todoAPI: TodoAPI = retrofit.create(TodoAPI::class.java)
}