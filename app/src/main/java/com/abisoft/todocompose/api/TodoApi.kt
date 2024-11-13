package com.example.myapplication.okhttp_client

import retrofit2.http.GET
import retrofit2.http.Query

interface TodoApi {

  @GET("/list")
  suspend fun getTodoList(): List<TodoDto>

}
