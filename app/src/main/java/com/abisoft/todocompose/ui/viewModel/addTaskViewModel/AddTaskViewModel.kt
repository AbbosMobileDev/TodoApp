package com.abisoft.todocompose.ui.viewModel.addTaskViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abisoft.todocompose.TodoItemsRepository
import com.abisoft.todocompose.model.ImportanceNetwork
import com.abisoft.todocompose.model.TodoItemNetwork
import com.abisoft.todocompose.model.TodoItemPost
import com.example.myapplication.okhttp_client.TodoDto
import kotlinx.coroutines.launch
import java.util.UUID


class AddTaskViewModel(private val repository: TodoItemsRepository) : ViewModel() {

        private val _todoList = MutableLiveData<List<TodoItemNetwork>>()
        val todoList: LiveData<List<TodoItemNetwork>> get() = _todoList

    fun addNewTask(task: TodoDto) {
        viewModelScope.launch {
            try {
                val currentRevision = repository.getCurrentRevision()

                // TodoDto obyektini TodoItemNetwork ga o'zgartirish
                val newTaskNetwork = TodoItemNetwork(
                    id = task.id ?: UUID.randomUUID().toString(),  // Agar task id bo'lmasa, yangi UUID hosil qilish
                    text = task.text.toString(),
                    importance = when (task.importance) {
                        "High" -> ImportanceNetwork.important
                        "Normal" -> ImportanceNetwork.basic
                        else -> ImportanceNetwork.low
                    },
                    deadline = task.deadline?.toLong(),
                    done = task.done,
                    color = task.color,
                    createdAt = task.createdAt?.toLong(),
                    modifiedAt = task.changedAt?.toLong(),
                    lastUpdatedBy = task.lastUpdatedBy.toString()
                )

                val newItemPost = TodoItemPost(
                    status = "created",  // Yangi item statusi
                    element = newTaskNetwork,  // Yangi task
                    revision = currentRevision  // Revision yuborish
                )

                val response = repository.addTodoItem(
                    todoItemPost = newItemPost,
                    revision = currentRevision
                )

                Log.d("TodoViewModel", "Response: ${response.body()}")

                if (response.isSuccessful) {
                    _todoList.value = _todoList.value?.plus((response.body()?.element ?: TodoItemNetwork("", "", ImportanceNetwork.low, 0, false, "", 0, 0, "")))
                } else {
                    val errorBody = response.errorBody()?.string()
                    println("Error: ${response.code()} - $errorBody")
                }
            } catch (e: Exception) {
                Log.e("TodoViewModel", "Error adding task: ${e.localizedMessage}")
                println("Error adding task: ${e.localizedMessage}")
            }
        }
    }

}

