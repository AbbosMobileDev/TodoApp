package com.abisoft.todocompose

import com.abisoft.todocompose.api.UpdateRequestData
import com.example.myapplication.okhttp_client.TodoApiService
import com.example.myapplication.okhttp_client.TodoDto
import retrofit2.Response

@Suppress("UNREACHABLE_CODE")
class TodoItemsRepository(private val apiService: TodoApiService) {
    suspend fun getTasksList(): List<TodoDto>? {
        val response = apiService.getTasksList()
        return if (response.isSuccessful) {
            response.body()?.list // list qaytariladi
        } else {
            null
        }
    } suspend fun updateTodo(
        id: String,
        updatedTask: TodoDto
    ): Response<TodoDto> {
        val updateRequest = UpdateRequestData(element = updatedTask)
        return apiService.updateTodo(
            id = id,
            element = updateRequest
        )
    }

    suspend fun addTask(revision: Int, task: TodoDto): Response<TodoDto> {
        // Task qo'shish uchun POST so'rovini yuborish
        return apiService.addTodo(revision, task)
    }

    suspend fun getCurrentRevision(): Int {
        // API'dan tasklar ro'yxatini olish
        val response = apiService.getTasksList()

        // Agar API javobi muvaffaqiyatli bo'lsa
        return if (response.isSuccessful) {
            // Headerdan 'X-Revision' qiymatini olish
            response.headers()["X-Revision"]?.toInt() ?: 0 // Agar revision bo'lmasa, 0 qaytaring
        } else {
            throw Exception("Failed to fetch the current revision") // Agar xato bo'lsa, xatolik chiqarish
        }
            println("Revision: ${response.headers()["X-Revision"]?.toInt()}")
    }

}




