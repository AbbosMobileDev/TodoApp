package com.abisoft.todocompose.datasource

import com.abisoft.todocompose.api.UpdateRequestData
import com.abisoft.todocompose.model.TodoItemNetwork
import com.abisoft.todocompose.model.TodoItemPost
import com.example.myapplication.okhttp_client.TodoApiService
import com.example.myapplication.okhttp_client.TodoDto
import retrofit2.Response

class TodoItemsDataSource(private val apiService: TodoApiService) {

    suspend fun fetchTasksList(): Response<TodoApiService.TodoItemResponse> {
        return apiService.getTasksList()
    }

    suspend fun updateTask(id: String, updateRequest: TodoItemPost, revision: Int): Response<TodoItemPost> {
        return apiService.updateTodo(
            id = id,
            todoItemPost = updateRequest,
            revision = revision
        )
    }

    suspend fun addTask(todoItemPost: TodoItemPost, revision: Int): Response<TodoItemPost> {
        return apiService.addTodoItem(
            todoItemPost = todoItemPost,
            revision = revision
        )
    }
}
