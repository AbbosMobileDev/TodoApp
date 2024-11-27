package com.abisoft.todocompose

import com.abisoft.todocompose.datasource.TodoItemsDataSource
import com.abisoft.todocompose.model.TodoItemNetwork
import com.abisoft.todocompose.model.TodoItemPost
import com.example.myapplication.okhttp_client.ApiClient
import com.example.myapplication.okhttp_client.TodoDto
import retrofit2.Response

class TodoItemsRepository(private val dataSource: TodoItemsDataSource) {

    suspend fun getTasksList(): List<TodoDto>? {
        val response = dataSource.fetchTasksList()
        return if (response.isSuccessful) {
            response.body()?.list
        } else {
            null
        }
    }

    suspend fun updateItem(id: String, updatedTask: TodoItemNetwork): Response<TodoItemPost> {
        val currentRevision = getCurrentRevision()

        val updateRequest = TodoItemPost(
            status = "updated",
            element = updatedTask,
            revision = currentRevision
        )

        return dataSource.updateTask(
            id = id,
            updateRequest = updateRequest,
            revision = currentRevision
        )
    }

    suspend fun addTodoItem(todoItemPost: TodoItemPost, revision: Int): Response<TodoItemPost> {
        return dataSource.addTask(
            todoItemPost = todoItemPost,
            revision = revision
        )
    }

    suspend fun getCurrentRevision(): Int {
        val response = dataSource.fetchTasksList()
        return if (response.isSuccessful) {
            response.headers()["X-Last-Known-Revision"]?.toInt() ?: 0
        } else {
            throw Exception("Failed to fetch the current revision")
        }
    }
}
