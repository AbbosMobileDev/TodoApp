package com.example.myapplication.okhttp_client

import RequestLoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

  private val client = OkHttpClient.Builder()
    .addInterceptor(RequestLoggingInterceptor()) // Custom interceptorni qo'shish
    .addInterceptor(HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY // Body va headerlarni loglash
    })
    .build()

  private  const val BASE_URL = "https://hive.mrdekk.ru/todo/"

  fun create(): TodoApiService {
    val retrofit = Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(client)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
    return retrofit.create(TodoApiService::class.java)
  }
}
