package com.abisoft.todocompose.ui.viewModel.addTaskViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abisoft.todocompose.TodoItemsRepository

@Suppress("UNCHECKED_CAST")
class AddViewModelFactory(private val repository: TodoItemsRepository): ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddTaskViewModel(repository) as T
    }
}