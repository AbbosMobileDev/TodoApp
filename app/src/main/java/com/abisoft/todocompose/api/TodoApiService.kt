package com.example.myapplication.okhttp_client

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoApiService {

    @GET("/list/")
    suspend fun getTodoList(
        @Header("Authorization") token: String,
        @Header("X-Last-Known-Revision") revision: Int
    ): Response<List<TodoDto>>

  @POST("list/")
  suspend fun addTodo(
    @Header("Authorization") token: String,
    @Header("X-Last-Known-Revision") revision: Int,
    @Body todoItem: TodoDto
  ): Response<TodoDto>

  @GET("list/{id}")
  suspend fun getTodoById(
    @Header("Authorization") token: String,
    @Path("id") id: String
  ): Response<TodoDto>

  @PUT("list/{id}")
  suspend fun updateTodo(
    @Header("Authorization") token: String,
    @Header("X-Last-Known-Revision") revision: Int,
    @Path("id") id: String,
    @Body todoItem: TodoDto
  ): Response<TodoDto>

  @DELETE("list/{id}")
   fun deleteTodo(
    @Header("Authorization") token: String,
    @Path("id") id: String
  ): Response<Unit>
}
