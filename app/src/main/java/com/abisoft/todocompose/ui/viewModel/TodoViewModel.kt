import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abisoft.todocompose.TodoItemsRepository
import com.example.myapplication.okhttp_client.TodoDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoItemsRepository) : ViewModel() {
    private val _todoItems = MutableStateFlow<List<TodoDto>>(emptyList())
    val todoItems: StateFlow<List<TodoDto>> = _todoItems

    fun fetchTodos() {
        viewModelScope.launch {
            val result = repository.getTodos("Earendil", 0) // Revision raqamini moslashtiring
            result.onSuccess { items ->
                _todoItems.value = items
            }.onFailure {
                // Xatoliklarni qayta ishlash
            }
        }
    }

    fun updateTaskCompletion(id: String, done: Boolean,revision: Int) {
        viewModelScope.launch {
            // Mavjud vazifani o'zgartirib, serverga yuboramiz
            val currentTask = _todoItems.value.find { it.id == id }
            if (currentTask != null) {
                val updatedTask = currentTask.copy(done = done)
                val updatedTaskDto = TodoDto(
                    id = updatedTask.id,
                    text = updatedTask.text,
                    importance = updatedTask.importance,
                    deadline = updatedTask.deadline,
                    done = updatedTask.done,
                    color = updatedTask.color,
                    createdAt = updatedTask.createdAt,
                    changedAt = updatedTask.changedAt,
                    lastUpdatedBy = updatedTask.lastUpdatedBy
                )
                val token = "Earendil" // Haqiqiy tokenni kiriting

                val result = repository.updateTodo(token, revision, id, updatedTaskDto)

                result.onSuccess {
                    // Agar muvaffaqiyatli bo'lsa, lokal ro'yxatni yangilaymiz
                    _todoItems.value = _todoItems.value.map {
                        if (it.id == id) updatedTask else it
                    }
                }.onFailure {

                }
            }
        }
    }


    fun deleteTask(id: String) {
        viewModelScope.launch {
            try {
                // API orqali vazifani o'chirish
                val token = "Earendil" // Haqiqiy tokenni olish
                val result = repository.deleteTodo(token, id)

                result.onSuccess {
                    // Agar muvaffaqiyatli o'chirilsa, lokal ro'yxatni yangilash
                    _todoItems.value = _todoItems.value.filter { it.id != id }
                }.onFailure {
                    // Xatolikni qayta ishlash (masalan, log yozish yoki foydalanuvchiga xabar ko'rsatish)
                    Log.e("TodoViewModel", "Error deleting task: ${it.message}")
                }
            } catch (e: Exception) {
                // Xatolikni qayta ishlash
                Log.e("TodoViewModel", "Error: ${e.message}")
            }
        }
    }

}
