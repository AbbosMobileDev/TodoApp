package com.abisoft.todocompose

import com.abisoft.todocompose.model.Importance
import com.abisoft.todocompose.model.ItemData
import java.util.Date

object TodoItemsRepository {

        private val tasks = mutableListOf<ItemData>()

        init {
            repeat(20) { index ->
                tasks.add(
                    ItemData(
                        id = "id_$index",
                        text = "Task description $index",
                        importance = when (index % 3) {
                            0 -> Importance.LOW
                            1 -> Importance.NORMAL
                            else -> Importance.URGENT
                        },
                        deadline = null,
                        isCompleted = false,
                        createdAt = Date(),
                        modifiedAt = null
                    )
                )
            }
        }

        fun getTasks(): List<ItemData> {
            return tasks
        }

        fun addTask(task: ItemData) {
            tasks.add(task)
        }

        fun deleteTask(id: String): Boolean {
            return tasks.removeIf { it.id == id }
        }


}


