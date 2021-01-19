package com.example.ulessondemo.network.model

import com.google.gson.annotations.SerializedName

data class LessonDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("icon")
    val iconUrl: String,
    @SerializedName("media_url")
    val mediaUrl: String,
    @SerializedName("chapter_id")
    val chapterId: Int,
    @SerializedName("subject_id")
    val subjectId: Int
)