package com.abisoft.todocompose.model

import com.google.gson.annotations.SerializedName

data class TodoModule(

    @SerializedName("id")
    val id: String? = null,
    @SerializedName("text")
    val text: String? = null,
    @SerializedName("importance")
    val importance: String? = null,
    @SerializedName("deadline")
    val deadline: Int? = null,
    @SerializedName("done")
    val done: Boolean = false,
    @SerializedName("color")
    val color: String? = null,
    @SerializedName("created_at")
    val createdAt: Int? = null,
    @SerializedName("changed_at")
    val changedAt: Int? = null,
    @SerializedName("last_updated_by")
    val lastUpdatedBy: String? = null,
)
