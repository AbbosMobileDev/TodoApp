import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abisoft.todocompose.TodoItemsRepository

class TodoViewModelFactory(private val repository: TodoItemsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TodoViewModel(repository) as T
    }
}
