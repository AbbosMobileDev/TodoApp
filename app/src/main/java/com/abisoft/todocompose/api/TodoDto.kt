package com.example.myapplication.okhttp_client

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TodoDto(
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


