package com.example.myapplication.okhttp_client

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
  private  const val BASE_URL = "https://hive.mrdekk.ru/todo/"
  private val okHttpClient = OkHttpClient.Builder()
    .build()

  fun create(): TodoApiService {
    val retrofit = Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(okHttpClient)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
    return retrofit.create(TodoApiService::class.java)
  }
}
