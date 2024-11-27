package com.abisoft.uimodule.ui.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abisoft.todocompose.TodoItemsRepository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class TodoViewModelFactory @Inject constructor(private val repository: TodoItemsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return com.abisoft.uimodule.ui.viewModel.TodoViewModel(repository) as T
    }
}
