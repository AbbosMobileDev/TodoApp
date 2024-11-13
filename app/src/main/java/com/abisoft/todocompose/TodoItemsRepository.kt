package com.abisoft.todocompose

import com.example.myapplication.okhttp_client.TodoApiService
import com.example.myapplication.okhttp_client.TodoDto

class TodoItemsRepository(private val apiService: TodoApiService) {

    // Elementlar ro'yxatini olish
    suspend fun getTodos(token: String, revision: Int): Result<List<TodoDto>> {
        return try {
            val response = apiService.getTodoList("Bearer $token", revision)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Ro'yxatga yangi element qo'shish
    suspend fun addTodo(token: String, revision: Int, todoItem: TodoDto): Result<TodoDto> {
        return try {
            val response = apiService.addTodo("Bearer $token", revision, todoItem)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Belgilangan ID bo'yicha elementni olish
    suspend fun getTodoById(token: String, id: String): Result<TodoDto> {
        return try {
            val response = apiService.getTodoById("Bearer $token", id)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Elementni o'zgartirish
    suspend fun updateTodo(token: String, revision: Int, id: String, todoItem: TodoDto): Result<TodoDto> {
        return try {
            val response = apiService.updateTodo("Bearer $token", revision, id, todoItem)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Elementni o'chirish
     fun deleteTodo(token: String, id: String): Result<Unit> {
        return try {
            val response = apiService.deleteTodo("Bearer $token", id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}




