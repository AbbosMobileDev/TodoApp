package com.abisoft.todocompose.model

import com.google.gson.annotations.SerializedName

data class TodoItemPost(val status: String, val element: TodoItemNetwork, val revision:Int)

data class TodoItemNetwork (
    @SerializedName("id")
    val id: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("importance")
    val importance: ImportanceNetwork,
    @SerializedName("deadline")
    val deadline: Long?,
    @SerializedName("done")
    val done: Boolean,
    @SerializedName("color")
    val color: String? = null,
    @SerializedName("created_at")
    val createdAt: Long?,
    @SerializedName("changed_at")
    val modifiedAt: Long?,
    @SerializedName("last_updated_by")
    val lastUpdatedBy: String,
    @SerializedName("files")
    val files: String? = null
)

enum class ImportanceNetwork {
    low,      // "низкая"
    basic,   // "обычная"
    important      // "срочная"

}
