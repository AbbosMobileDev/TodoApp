import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abisoft.todocompose.TodoItemsRepository
import com.abisoft.todocompose.model.TodoItemNetwork
import com.example.myapplication.okhttp_client.TodoDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoItemsRepository) : ViewModel() {
    private var _todoList = MutableStateFlow<List<TodoDto>>(emptyList())
    val todoList: StateFlow<List<TodoDto>> get() = _todoList

    private val _tasks = MutableStateFlow<List<TodoDto>>(emptyList())
    val tasks: StateFlow<List<TodoDto>> = _tasks
    private var revision = 0
    fun fetchTasks() {
        viewModelScope.launch {
            try {
                val tasks = repository.getTasksList()
                tasks?.let {
                    _tasks.value = it // UIga ma'lumot yuborish
                }            } catch (e: Exception) {
                // Xatolikni qayta ishlash
            }
        }
    }
    fun getTasksList() {
        viewModelScope.launch {
            _todoList.value = repository.getTasksList() ?: emptyList()
        }
    }// Update task done status
    suspend fun updateTaskDone(task: TodoItemNetwork) {
        val updatedTask = task.copy(done = !task.done)  // Flip the done status
        val response = repository.updateItem(task.id.toString(), updatedTask)
        if (response.isSuccessful) {
            // Update successful, reload the task list to reflect the change
            getTasksList()
        }
    }

}








