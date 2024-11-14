package com.abisoft.todocompose

import com.abisoft.todocompose.api.UpdateRequestData
import com.abisoft.todocompose.model.TodoItemNetwork
import com.abisoft.todocompose.model.TodoItemPost
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
    }
    suspend fun updateItem(id: String, updatedTask: TodoItemNetwork): Response<TodoItemPost> {
        // Hozirgi revisionni olish
        val currentRevision = getCurrentRevision()

        // Yangilangan taskni API kutgan formatga o'zgartirish
        val updateRequest = TodoItemPost(
            status = "updated", // Taskni yangilash statusi, bu o'zgartirilishi mumkin
            element = updatedTask, // Yangilangan task
            revision = currentRevision // Revisionni yuborish
        )

        // API'ga yangilangan taskni yuborish
        return apiService.updateTodo(
            id = id, // Taskning ID sini yuborish
            todoItemPost = updateRequest, // Yangilanish talabini yuborish
            revision = currentRevision // Revision bilan yangilash
        )
    }

    suspend fun addTodoItem(todoItemPost: TodoItemPost, revision: Int): Response<TodoItemPost> {
        return apiService.addTodoItem(
            todoItemPost = todoItemPost,  // Yangi taskni yuborish
            revision = revision  // Revisionni yuborish
        )
    }


    suspend fun getCurrentRevision(): Int {
        // API'dan tasklar ro'yxatini olish
        val response = apiService.getTasksList()

        // Agar API javobi muvaffaqiyatli bo'lsa
        return if (response.isSuccessful) {
            // Headerdan 'X-Revision' qiymatini olish
            response.headers()["X-Last-Known-Revision"]?.toInt() ?: 0 // Agar revision bo'lmasa, 0 qaytaring
        } else {
            throw Exception("Failed to fetch the current revision") // Agar xato bo'lsa, xatolik chiqarish
        }
            println("Revision: ${response.headers()["X-Last-Known-Revision"]?.toInt()}")
    }

}




