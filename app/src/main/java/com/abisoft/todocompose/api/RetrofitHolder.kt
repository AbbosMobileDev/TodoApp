package com.example.myapplication.okhttp_client

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHolder {

  private val okHttpClient = OkHttpClient.Builder()
    .build()

  private val retrofit = Retrofit.Builder()
    .baseUrl("https://beta.mrdekk.ru/todo")
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()

  val todoApi: TodoApi = retrofit.create(TodoApi::class.java)
}
