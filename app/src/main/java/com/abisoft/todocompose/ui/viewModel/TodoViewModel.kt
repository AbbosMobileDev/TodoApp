import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abisoft.todocompose.TodoItemsRepository
import com.abisoft.todocompose.api.UpdateRequestData
import com.abisoft.todocompose.model.ImportanceNetwork
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
    }
    fun updateTaskDone(task: TodoDto) {
        viewModelScope.launch {
            try {
                // Hozirgi revisionni olish
                val currentRevision = repository.getCurrentRevision()

                // Taskning 'done' qiymatini teskari qilish
                val updatedTask = task.copy(done = !task.done)

                // TodoDto obyektini TodoItemNetwork ga o'zgartirish
                val updatedTaskNetwork = TodoItemNetwork(
                    id = updatedTask.id ?: "",
                    text = updatedTask.text.toString(),
                    importance = when (updatedTask.importance) {
                        "High" -> ImportanceNetwork.important
                        "Normal" -> ImportanceNetwork.basic
                        else -> ImportanceNetwork.low
                    },
                    deadline = updatedTask.deadline?.toLong(),
                    done = updatedTask.done,
                    color = updatedTask.color,
                    createdAt = updatedTask.createdAt?.toLong(),
                    modifiedAt = updatedTask.changedAt?.toLong(),
                    lastUpdatedBy = updatedTask.lastUpdatedBy.toString()
                )

                // API ga yangilangan taskni yuborish
                val response = repository.updateItem(
                    id = updatedTask.id ?: "",
                    updatedTask = updatedTaskNetwork
                )

                Log.d("TodoViewModel", "Response: ${response.body()}")

                // Agar muvaffaqiyatli bo'lsa, taskni yangilangan holatga qo'shish
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








