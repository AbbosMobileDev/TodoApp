package com.abisoft.todocompose.model

import com.google.gson.annotations.SerializedName

data class TodoItemPost(val status: String, val element: TodoItemNetwork, val revision:Int)

data class TodoItemNetwork (
    val id: String,
    val text: String,
    val importance: ImportanceNetwork,
    val deadline: Long?,
    val done: Boolean,
    val color: String? = null,
    @SerializedName("created_at")
    val createdAt: Long?,
    @SerializedName("changed_at")
    val modifiedAt: Long?,
    @SerializedName("last_updated_by")
    val lastUpdatedBy: String,
    val files: String? = null
)

enum class ImportanceNetwork {
    low,      // "низкая"
    basic,   // "обычная"
    important      // "срочная"

}
