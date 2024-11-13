package com.abisoft.todocompose.ui.viewModel.addTaskViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abisoft.todocompose.TodoItemsRepository
import com.example.myapplication.okhttp_client.TodoDto
import kotlinx.coroutines.launch


class AddTaskViewModel(private val repository: TodoItemsRepository) : ViewModel() {

        private val _todoList = MutableLiveData<List<TodoDto>>()
        val todoList: LiveData<List<TodoDto>> get() = _todoList

        // Task qo'shish
        fun addTask(newTask: TodoDto) {
            viewModelScope.launch {
                try {
                    // Hozirgi revisionni olish
                    val currentRevision = repository.getCurrentRevision()

                    // Taskni serverga qo'shish
                    val response = repository.addTask(revision = currentRevision, task = newTask)

                    if (response.isSuccessful) {
                        // Muvaffaqiyatli qo'shilgan taskni ro'yxatga qo'shish
                        _todoList.value = _todoList.value?.plus(response.body()!!)
                    } else {
                        val errorBody = response.errorBody()?.string()
                        println("Error: ${response.code()} - $errorBody")
                    }
                } catch (e: Exception) {
                    // Xatolikni qayta ishlash
                    e.printStackTrace()
                }
            }
        }
    }

