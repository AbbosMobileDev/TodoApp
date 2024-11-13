import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abisoft.todocompose.TodoItemsRepository
import com.abisoft.todocompose.api.UpdateRequestData
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
    }
    fun updateTaskDone(task: TodoDto) {
        viewModelScope.launch {
            try {
                val currentRevision = repository.getCurrentRevision() // Fetch the current revision
                val updatedTask = task.copy(done = !task.done) // Toggle the 'done' field

                val response = repository.updateTodo(
                    id = task.id ?: "",
                    updatedTask = updatedTask
                )
                Log.d("TodoViewModel", "Response: ${response.body()}")

                if (response.isSuccessful) {
                    _todoList.value = _todoList.value.map {
                        if (it.id == task.id) updatedTask else it
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    println("Error: ${response.code()} - $errorBody")
                }
            } catch (e: Exception) {
                Log.e("TodoViewModel", "Error updating task: ${e.localizedMessage}")

                println("Error updating task: ${e.localizedMessage}")
            }
        }
    }



}




