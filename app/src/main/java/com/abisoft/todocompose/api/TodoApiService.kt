package com.example.myapplication.okhttp_client

import com.abisoft.todocompose.api.UpdateRequestData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoApiService {

    @Headers("Authorization: Bearer Earendil")
    @GET("list")
    suspend fun getTasksList(): Response<TodoItemResponse>
    data class TodoItemResponse(val list: List<TodoDto>, val revision: Int)

  @POST("list/")
  @Headers("Authorization: Bearer Earendil")
  suspend fun addTodo(
    @Header("X-Last-Known-Revision") revision: Int,
    @Body todoItem: TodoDto
  ): Response<TodoDto>

    @Headers("Authorization: Bearer Earendil")
    @GET("list/{id}")
    suspend fun getTodoItem(
        @Path("id") id: String
    ): Response<TodoDto>

  @Headers("Authorization: Bearer Earendil")
  @PUT("list/{id}")
  suspend fun updateTodo(
      @Path("id") id: String,
      @Body element: UpdateRequestData,
  ): Response<TodoDto>

  @DELETE("list/{id}")
   fun deleteTodo(
    @Header("Authorization") token: String,
    @Path("id") id: String
  ): Response<Unit>
}
